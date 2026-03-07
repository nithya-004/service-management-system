import React from "react";
import { Drawer, List, ListItem, ListItemText, AppBar, Toolbar, Typography, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";

const drawerWidth = 240;

function Layout({ children }) {
  const navigate = useNavigate();

  return (
    <Box sx={{ display: "flex" }}>
      
      {/* Top Navbar */}
      <AppBar position="fixed" sx={{ zIndex: 1201 }}>
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
            Service Management System
          </Typography>
        </Toolbar>
      </AppBar>

      {/* Sidebar */}
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: "border-box" },
        }}
      >
        <Toolbar />
        <List>
          <ListItem button onClick={() => navigate("/dashboard")}>
            <ListItemText primary="Dashboard" />
          </ListItem>

          <ListItem button onClick={() => navigate("/requests")}>
            <ListItemText primary="Requests" />
          </ListItem>

          <ListItem button onClick={() => navigate("/bills")}>
            <ListItemText primary="Bills" />
          </ListItem>
        </List>
      </Drawer>

      {/* Main Content */}
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        {children}
      </Box>
    </Box>
  );
}

export default Layout;