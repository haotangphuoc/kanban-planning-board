import { React, useState } from "react";
import {
  Container,
  Row,
  Nav,
  Stack,
  Button,
  ListGroup,
  Card,
} from "react-bootstrap";

const CreateWorkspace = () => {
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <Stack
            className="mx-auto"
            direction="horizontal"
            style={{ paddingTop: 38 }}
          >
            <div style={{ paddingBottom: 12 }}>
              <Card.Title>
                <Nav.Link href="../Pages/Homepage.js">&#60; Homepage</Nav.Link>
              </Card.Title>
            </div>
          </Stack>
          <div>
            <h2 style={{ paddingTop: 38 }}>Workspaces</h2>
            <br />
            <h4>Workspace Name</h4>
            <h6>Description</h6>
            <br />
            <Card>
              <Card.Header>
                <Nav variant="tabs" defaultActiveKey="#boards">
                  <Nav.Item>
                    <Nav.Link href="#boards">Boards</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="../Pages/CreateBoards.js">
                      Create a board
                    </Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="../Pages/Members.js">Members</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="../Pages/Settings.js">Settings</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Card.Header>
              <Card.Body>
                <Card.Title>
                  <Nav.Link href="../Pages/Board.js">Board Title</Nav.Link>
                </Card.Title>
                <Card.Text>Description</Card.Text>
                <Card.Title>
                  <Nav.Link href="#">Board Title</Nav.Link>
                </Card.Title>
                <Card.Text>Description</Card.Text>
              </Card.Body>
            </Card>
            <br />
            <h4>Workspace Name 2</h4>
            <h6>Description</h6>
            <br />
            <Card>
              <Card.Header>
                <Nav variant="tabs" defaultActiveKey="#boards">
                  <Nav.Item>
                    <Nav.Link href="#boards">Boards</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="../Pages/CreateBoards.js">
                      Create a board
                    </Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="../Pages/Members.js">Members</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link href="../Pages/Settings.js">Settings</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Card.Header>
              <Card.Body>
                <Card.Title>
                  <Nav.Link href="../Pages/Board.js">Board Title</Nav.Link>
                </Card.Title>
                <Card.Text>Description</Card.Text>
                <Card.Title>
                  <Nav.Link href="#">Board Title</Nav.Link>
                </Card.Title>
                <Card.Text>Description</Card.Text>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default CreateWorkspace;
