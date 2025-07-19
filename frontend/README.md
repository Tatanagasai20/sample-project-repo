# Priacc HR System - React Frontend

## Overview

This is a modern HR management system built with React, TypeScript, and Material UI. The application provides a comprehensive solution for managing various HR operations including leave management, attendance tracking, and HR administration.

## Features

- **Dashboard**: Overview of key metrics and quick access to main features
- **Leave Management**: Apply for leaves, track approvals, and view leave history
- **Attendance Tracking**: Check-in/out functionality and attendance history
- **HR Operations**: Access to various HR modules like employee management, recruitment, etc.
- **AI Chatbot**: Get instant answers to HR-related queries

## Tech Stack

- **React**: Frontend library for building user interfaces
- **TypeScript**: Static typing for JavaScript
- **Material UI**: React component library implementing Google's Material Design
- **Framer Motion**: Animation library for React
- **React Router**: Routing library for React
- **Recharts**: Charting library for React

## Project Structure

```
priacc-hr-system-react/
├── public/                # Static files
├── src/
│   ├── components/        # Reusable components
│   │   ├── ChatBot.tsx    # AI chatbot component
│   │   └── Header.tsx     # Application header
│   ├── pages/             # Page components
│   │   ├── Home.tsx       # Landing page
│   │   ├── Dashboard.tsx  # Dashboard page
│   │   ├── Leave.tsx      # Leave management page
│   │   ├── Attendance.tsx # Attendance tracking page
│   │   └── HR.tsx         # HR operations page
│   ├── App.tsx            # Main application component
│   ├── main.tsx           # Entry point
│   └── index.css          # Global styles
├── package.json           # Dependencies and scripts
├── tsconfig.json          # TypeScript configuration
├── vite.config.ts         # Vite configuration
└── README.md              # Project documentation
```

## Getting Started

### Prerequisites

- Node.js (v14 or later)
- npm or yarn

### Installation

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/priacc-hr-system-react.git
   cd priacc-hr-system-react
   ```

2. Install dependencies
   ```bash
   npm install
   # or
   yarn install
   ```

3. Start the development server
   ```bash
   npm run dev
   # or
   yarn dev
   ```

4. Open your browser and navigate to `http://localhost:5173`

## Building for Production

```bash
npm run build
# or
yarn build
```

The build artifacts will be stored in the `dist/` directory.

## Features to be Implemented

- User authentication and authorization
- Integration with backend API
- Data persistence
- Notifications system
- Advanced reporting and analytics
- Mobile responsiveness improvements

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Design inspiration from modern HR management systems
- Material UI for the component library
- Framer Motion for animations