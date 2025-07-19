import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import axios from 'axios';
import jwtDecode from 'jwt-decode';

interface User {
  id: string;
  email: string;
  name: string;
  role: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  checkAuth: () => Promise<boolean>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const initAuth = async () => {
      await checkAuth();
      setIsLoading(false);
    };

    initAuth();
  }, []);

  const checkAuth = async (): Promise<boolean> => {
    const storedToken = localStorage.getItem(import.meta.env.VITE_AUTH_TOKEN_KEY || 'hr_system_token');
    
    if (!storedToken) {
      setUser(null);
      setToken(null);
      return false;
    }

    try {
      // Verify token validity
      const decoded: any = jwtDecode(storedToken);
      const currentTime = Date.now() / 1000;
      
      if (decoded.exp < currentTime) {
        // Token expired
        localStorage.removeItem(import.meta.env.VITE_AUTH_TOKEN_KEY || 'hr_system_token');
        setUser(null);
        setToken(null);
        return false;
      }

      // Set axios default header
      axios.defaults.headers.common['Authorization'] = `Bearer ${storedToken}`;
      
      // Set user from decoded token
      setUser({
        id: decoded.sub,
        email: decoded.email,
        name: decoded.name,
        role: decoded.role
      });
      setToken(storedToken);
      return true;
    } catch (error) {
      console.error('Token validation error:', error);
      localStorage.removeItem(import.meta.env.VITE_AUTH_TOKEN_KEY || 'hr_system_token');
      setUser(null);
      setToken(null);
      return false;
    }
  };

  const login = async (email: string, password: string): Promise<void> => {
    try {
      const response = await axios.post(`${import.meta.env.VITE_API_URL}/auth/login`, {
        email,
        password,
      });

      const { token } = response.data;
      localStorage.setItem(import.meta.env.VITE_AUTH_TOKEN_KEY || 'hr_system_token', token);
      
      // Set axios default header
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      
      // Decode and set user
      const decoded: any = jwtDecode(token);
      setUser({
        id: decoded.sub,
        email: decoded.email,
        name: decoded.name,
        role: decoded.role
      });
      setToken(token);
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem(import.meta.env.VITE_AUTH_TOKEN_KEY || 'hr_system_token');
    delete axios.defaults.headers.common['Authorization'];
    setUser(null);
    setToken(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        isAuthenticated: !!user,
        isLoading,
        login,
        logout,
        checkAuth,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};