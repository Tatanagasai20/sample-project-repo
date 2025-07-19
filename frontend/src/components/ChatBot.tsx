import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { 
  Box, 
  Card, 
  CardHeader, 
  CardContent, 
  Typography, 
  IconButton, 
  TextField, 
  Button, 
  Avatar, 
  Paper,
  Fab
} from '@mui/material';
import { 
  Close as CloseIcon, 
  Send as SendIcon, 
  ChatBubble as ChatBubbleIcon,
  SmartToy as BotIcon,
  Person as PersonIcon
} from '@mui/icons-material';

interface Message {
  id: number;
  text: string;
  sender: 'user' | 'bot';
  timestamp: Date;
}

const predefinedResponses: { [key: string]: string } = {
  leave:
    "You can apply for leave through the Leave Management section. Your current leave balance is 12 days. Would you like me to guide you through the application process?",
  attendance:
    "Your attendance this week is 4/5 days present with 36 hours logged. You can check in/out from the Attendance page. Is there anything specific about your attendance you'd like to know?",
  hr: "Our HR department handles employee management, recruitment, performance reviews, and payroll. You can access HR operations from the main dashboard. What HR service do you need help with?",
  policy:
    "Company policies are available in the HR section under Policy Management. Common policies include leave policy, work from home guidelines, and code of conduct. Which policy would you like to know about?",
  payroll:
    "Payroll information and salary slips are processed monthly. You can find your payroll details in the HR Operations section under Payroll & Benefits. Contact HR for specific payroll queries.",
  benefits:
    "Employee benefits include health insurance, retirement plans, and professional development opportunities. Check the HR Benefits section for detailed information about your benefits package.",
  default:
    "I'm here to help with HR-related questions! You can ask me about leave applications, attendance tracking, company policies, payroll, benefits, or any other HR matters. How can I assist you today?",
};

const ChatBot: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState<Message[]>([
    {
      id: 1,
      text: "Hello! I'm your HR assistant. I can help you with leave applications, attendance queries, company policies, and more. How can I assist you today?",
      sender: "bot",
      timestamp: new Date(),
    },
  ]);
  const [inputValue, setInputValue] = useState("");

  const handleSendMessage = () => {
    if (!inputValue.trim()) return;

    const userMessage: Message = {
      id: messages.length + 1,
      text: inputValue,
      sender: "user",
      timestamp: new Date(),
    };

    setMessages((prev) => [...prev, userMessage]);
    setInputValue("");

    // Simulate bot response
    setTimeout(() => {
      const botResponse = getBotResponse(inputValue.toLowerCase());
      const botMessage: Message = {
        id: messages.length + 2,
        text: botResponse,
        sender: "bot",
        timestamp: new Date(),
      };
      setMessages((prev) => [...prev, botMessage]);
    }, 1000);
  };

  const getBotResponse = (input: string): string => {
    for (const [key, response] of Object.entries(predefinedResponses)) {
      if (key !== "default" && input.includes(key)) {
        return response;
      }
    }
    return predefinedResponses.default;
  };

  const handleKeyPress = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      handleSendMessage();
    }
  };

  return (
    <>
      {/* Chat Button */}
      <motion.div
        style={{ position: 'fixed', bottom: 24, right: 24, zIndex: 1000 }}
        initial={{ scale: 0 }}
        animate={{ scale: 1 }}
        transition={{ delay: 1, type: "spring", stiffness: 300 }}
      >
        <Fab
          color="primary"
          onClick={() => setIsOpen(true)}
          sx={{ 
            width: 64, 
            height: 64,
            background: 'linear-gradient(to right, #3b82f6, #4f46e5)',
            '&:hover': {
              background: 'linear-gradient(to right, #2563eb, #4338ca)',
            }
          }}
        >
          <ChatBubbleIcon fontSize="large" />
        </Fab>
      </motion.div>

      {/* Chat Window */}
      <AnimatePresence>
        {isOpen && (
          <motion.div
            style={{ 
              position: 'fixed', 
              bottom: 96, 
              right: 24, 
              zIndex: 1000,
              width: 380,
              maxWidth: 'calc(100vw - 48px)',
            }}
            initial={{ opacity: 0, y: 20, scale: 0.95 }}
            animate={{ opacity: 1, y: 0, scale: 1 }}
            exit={{ opacity: 0, y: 20, scale: 0.95 }}
            transition={{ type: "spring", stiffness: 300, damping: 30 }}
          >
            <Card sx={{ 
              boxShadow: '0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
              borderRadius: 2,
              overflow: 'hidden',
              bgcolor: 'background.paper',
            }}>
              <CardHeader
                title={
                  <Box display="flex" alignItems="center" gap={1}>
                    <BotIcon />
                    <Typography variant="h6">HR Assistant</Typography>
                  </Box>
                }
                action={
                  <IconButton onClick={() => setIsOpen(false)} size="small">
                    <CloseIcon />
                  </IconButton>
                }
                sx={{ 
                  background: 'linear-gradient(to right, #3b82f6, #4f46e5)',
                  color: 'white',
                  py: 1.5,
                }}
              />
              <CardContent sx={{ p: 0 }}>
                {/* Messages */}
                <Box sx={{ 
                  height: 320, 
                  overflowY: 'auto', 
                  p: 2, 
                  display: 'flex', 
                  flexDirection: 'column', 
                  gap: 2,
                  bgcolor: '#f8fafc',
                }}>
                  {messages.map((message) => (
                    <motion.div
                      key={message.id}
                      initial={{ opacity: 0, y: 10 }}
                      animate={{ opacity: 1, y: 0 }}
                      style={{ 
                        alignSelf: message.sender === "user" ? "flex-end" : "flex-start",
                        maxWidth: '80%',
                      }}
                    >
                      <Box sx={{ display: 'flex', gap: 1, alignItems: 'flex-start' }}>
                        {message.sender === "bot" && (
                          <Avatar 
                            sx={{ 
                              width: 32, 
                              height: 32, 
                              bgcolor: 'grey.200',
                              color: 'primary.main',
                            }}
                          >
                            <BotIcon fontSize="small" />
                          </Avatar>
                        )}
                        <Paper 
                          elevation={0} 
                          sx={{
                            p: 1.5,
                            borderRadius: 2,
                            bgcolor: message.sender === "user" ? 'primary.main' : 'background.paper',
                            color: message.sender === "user" ? 'white' : 'text.primary',
                            border: message.sender === "bot" ? 1 : 0,
                            borderColor: 'grey.200',
                          }}
                        >
                          <Typography variant="body2">{message.text}</Typography>
                          <Typography 
                            variant="caption" 
                            sx={{ 
                              display: 'block', 
                              mt: 0.5,
                              color: message.sender === "user" ? 'primary.light' : 'text.secondary',
                            }}
                          >
                            {message.timestamp.toLocaleTimeString([], {
                              hour: "2-digit",
                              minute: "2-digit",
                            })}
                          </Typography>
                        </Paper>
                        {message.sender === "user" && (
                          <Avatar 
                            sx={{ 
                              width: 32, 
                              height: 32, 
                              bgcolor: 'primary.main',
                            }}
                          >
                            <PersonIcon fontSize="small" />
                          </Avatar>
                        )}
                      </Box>
                    </motion.div>
                  ))}
                </Box>

                {/* Input */}
                <Box sx={{ p: 2, borderTop: 1, borderColor: 'divider', bgcolor: 'white' }}>
                  <Box sx={{ display: 'flex', gap: 1 }}>
                    <TextField
                      fullWidth
                      size="small"
                      value={inputValue}
                      onChange={(e) => setInputValue(e.target.value)}
                      placeholder="Ask me anything about HR..."
                      onKeyPress={handleKeyPress}
                      variant="outlined"
                    />
                    <Button 
                      variant="contained" 
                      onClick={handleSendMessage}
                      disabled={!inputValue.trim()}
                      sx={{ minWidth: 'unset', px: 2 }}
                    >
                      <SendIcon fontSize="small" />
                    </Button>
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
};

export default ChatBot;