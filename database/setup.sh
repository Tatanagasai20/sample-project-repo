#!/bin/bash

# HR System Database Setup Script
# This script helps you set up databases for the HR System

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_header() {
    echo -e "${BLUE}===============================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}===============================================${NC}"
}

# Check if Docker is installed
check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed. Please install Docker first."
        echo "Visit: https://docs.docker.com/get-docker/"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose is not installed. Please install Docker Compose first."
        echo "Visit: https://docs.docker.com/compose/install/"
        exit 1
    fi
    
    print_status "Docker and Docker Compose are installed"
}

# Check if ports are available
check_ports() {
    local ports=("5432" "3306" "8081" "8082" "6379" "8083")
    local busy_ports=()
    
    for port in "${ports[@]}"; do
        if netstat -tuln 2>/dev/null | grep -q ":$port " || ss -tuln 2>/dev/null | grep -q ":$port "; then
            busy_ports+=($port)
        fi
    done
    
    if [ ${#busy_ports[@]} -gt 0 ]; then
        print_warning "The following ports are already in use: ${busy_ports[*]}"
        print_warning "You may need to stop services using these ports or modify docker-compose.yml"
        read -p "Do you want to continue anyway? (y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 1
        fi
    else
        print_status "All required ports are available"
    fi
}

# Setup PostgreSQL
setup_postgresql() {
    print_header "Setting up PostgreSQL Database"
    
    print_status "Starting PostgreSQL and pgAdmin..."
    docker-compose up -d postgres-hr pgadmin
    
    print_status "Waiting for PostgreSQL to be ready..."
    sleep 10
    
    # Wait for PostgreSQL to be ready
    while ! docker exec hr_postgres pg_isready -U postgres > /dev/null 2>&1; do
        print_status "Waiting for PostgreSQL to start..."
        sleep 5
    done
    
    print_status "PostgreSQL is ready!"
    echo
    echo "PostgreSQL Connection Details:"
    echo "  Host: localhost"
    echo "  Port: 5432"
    echo "  Database: priacc_hr_system"
    echo "  Username: postgres"
    echo "  Password: postgres"
    echo
    echo "pgAdmin Access:"
    echo "  URL: http://localhost:8081"
    echo "  Email: admin@priacc.com"
    echo "  Password: admin123"
}

# Setup MySQL
setup_mysql() {
    print_header "Setting up MySQL Database"
    
    print_status "Starting MySQL and phpMyAdmin..."
    docker-compose up -d mysql-hr phpmyadmin
    
    print_status "Waiting for MySQL to be ready..."
    sleep 15
    
    # Wait for MySQL to be ready
    while ! docker exec hr_mysql mysqladmin ping -h localhost --silent > /dev/null 2>&1; do
        print_status "Waiting for MySQL to start..."
        sleep 5
    done
    
    print_status "MySQL is ready!"
    echo
    echo "MySQL Connection Details:"
    echo "  Host: localhost"
    echo "  Port: 3306"
    echo "  Database: priacc_hr_system"
    echo "  Username: hruser"
    echo "  Password: hrpassword"
    echo "  Root Password: rootpassword"
    echo
    echo "phpMyAdmin Access:"
    echo "  URL: http://localhost:8082"
    echo "  Username: hruser"
    echo "  Password: hrpassword"
}

# Setup Redis
setup_redis() {
    print_header "Setting up Redis Cache"
    
    print_status "Starting Redis and Redis Commander..."
    docker-compose up -d redis-hr redis-commander
    
    print_status "Waiting for Redis to be ready..."
    sleep 5
    
    print_status "Redis is ready!"
    echo
    echo "Redis Connection Details:"
    echo "  Host: localhost"
    echo "  Port: 6379"
    echo "  Password: (none)"
    echo
    echo "Redis Commander Access:"
    echo "  URL: http://localhost:8083"
}

# Setup all databases
setup_all() {
    print_header "Setting up All Database Services"
    
    print_status "Starting all services..."
    docker-compose up -d
    
    print_status "Waiting for services to be ready..."
    sleep 20
    
    # Wait for PostgreSQL
    while ! docker exec hr_postgres pg_isready -U postgres > /dev/null 2>&1; do
        print_status "Waiting for PostgreSQL..."
        sleep 5
    done
    
    # Wait for MySQL
    while ! docker exec hr_mysql mysqladmin ping -h localhost --silent > /dev/null 2>&1; do
        print_status "Waiting for MySQL..."
        sleep 5
    done
    
    print_status "All services are ready!"
    print_services_info
}

# Print services information
print_services_info() {
    echo
    print_header "Service Access Information"
    echo
    echo "Database Services:"
    echo "  PostgreSQL: localhost:5432 (postgres/postgres)"
    echo "  MySQL: localhost:3306 (hruser/hrpassword)"
    echo "  Redis: localhost:6379 (no auth)"
    echo
    echo "Management Interfaces:"
    echo "  pgAdmin (PostgreSQL): http://localhost:8081"
    echo "    Email: admin@priacc.com, Password: admin123"
    echo "  phpMyAdmin (MySQL): http://localhost:8082"
    echo "    Username: hruser, Password: hrpassword"
    echo "  Redis Commander: http://localhost:8083"
    echo "  H2 Console (when app running): http://localhost:8080/h2-console"
    echo
    echo "Spring Boot Profiles:"
    echo "  Default (H2): mvn spring-boot:run"
    echo "  PostgreSQL: mvn spring-boot:run -Dspring.profiles.active=postgres"
    echo "  MySQL: mvn spring-boot:run -Dspring.profiles.active=mysql"
}

# Cleanup function
cleanup() {
    print_header "Cleaning Up Database Services"
    
    print_status "Stopping all services..."
    docker-compose down
    
    read -p "Do you want to remove all data volumes? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        print_status "Removing data volumes..."
        docker-compose down -v
        print_status "All data has been removed"
    fi
    
    print_status "Cleanup completed"
}

# Status function
status() {
    print_header "Database Services Status"
    
    echo "Docker Compose Services:"
    docker-compose ps
    
    echo
    echo "Container Health:"
    docker ps --filter "name=hr_" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
}

# Show help
show_help() {
    echo "HR System Database Setup Script"
    echo
    echo "Usage: $0 [OPTION]"
    echo
    echo "Options:"
    echo "  postgresql    Setup PostgreSQL database only"
    echo "  mysql        Setup MySQL database only"
    echo "  redis        Setup Redis cache only"
    echo "  all          Setup all services (default)"
    echo "  status       Show status of running services"
    echo "  cleanup      Stop services and optionally remove data"
    echo "  help         Show this help message"
    echo
    echo "Examples:"
    echo "  $0                    # Setup all services"
    echo "  $0 postgresql        # Setup PostgreSQL only"
    echo "  $0 mysql            # Setup MySQL only"
    echo "  $0 status           # Check service status"
    echo "  $0 cleanup          # Stop and cleanup services"
}

# Main script logic
main() {
    # Change to script directory
    cd "$(dirname "$0")"
    
    # Check prerequisites
    check_docker
    
    case "${1:-all}" in
        postgresql)
            check_ports
            setup_postgresql
            ;;
        mysql)
            check_ports
            setup_mysql
            ;;
        redis)
            check_ports
            setup_redis
            ;;
        all)
            check_ports
            setup_all
            ;;
        status)
            status
            ;;
        cleanup)
            cleanup
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            print_error "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"