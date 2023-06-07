import { React, useState } from "react";
import {
  Container,
  Row,
  Stack,
  Nav,
  Button,
  ListGroup,
  Card,
} from "react-bootstrap";

const Board = () => {
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <Stack
              className="mx-auto"
              direction="horizontal"
              style={{ paddingTop: 38 }}
            >
              <div style={{ paddingBottom: 12 }}>
                <Card.Title>
                  <Nav.Link href="../Pages/Workspace.js">
                    &#60; Workspace
                  </Nav.Link>
                </Card.Title>
              </div>
            </Stack>

            <h3 style={{ paddingTop: 38 }}>Board Name</h3>
            <h6>Description</h6>
            <br />
            <Card style={{ width: "18rem" }}>
              <Card.Header>Featured</Card.Header>
              <ListGroup variant="flush">
                <ListGroup.Item>Cras justo odio</ListGroup.Item>
                <ListGroup.Item>Dapibus ac facilisis in</ListGroup.Item>
                <ListGroup.Item>Vestibulum at eros</ListGroup.Item>
              </ListGroup>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Board;
