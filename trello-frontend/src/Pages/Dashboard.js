import React from "react";
import { Container, Row, Card, CardGroup, Form } from "react-bootstrap";

const Dashboard = () => {
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <h1 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Welcome to Trello-Clone!
            </h1>
          </div>
        </Row>
        <Row>
          <div>
            <h3>Title</h3>
            <Card body>Information</Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Dashboard;
