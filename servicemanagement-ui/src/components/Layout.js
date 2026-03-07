import React from "react";
import { Box, Drawer, List, ListItem, ListItemText, AppBar, Toolbar, Typography } from "@mui/material";

const drawerWidth = 220;

function Layout({ children }) {
  return (
    <Box sx={{ display: "flex" }}>

      {/* Top Bar */}
      <AppBar position="fixed" sx={{ zIndex: 1201 }}>
        <Toolbar>
          <Typography variant="h6">
            Service Management System
          </Typography>
        </Toolbar>
      </AppBar>

      {/* Sidebar */}
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          [`& .MuiDrawer-paper`]: {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        <Toolbar />
        <List>

          <ListItem button>
            <ListItemText primary="Dashboard" />
          </ListItem>

          <ListItem button>
            <ListItemText primary="Requests" />
          </ListItem>

          <ListItem button>
            <ListItemText primary="Profile" />
          </ListItem>

        </List>
      </Drawer>

      {/* Main Content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          marginLeft: `${drawerWidth}px`,
          marginTop: "64px",
        }}
      >
        {children}
      </Box>

    </Box>
  );
}

export default Layout;