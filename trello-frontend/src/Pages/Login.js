import { React, useState } from "react";
import { Container, Row, Form, Stack, Button } from "react-bootstrap";

const Login = () => {
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <h1 style={{ paddingTop: 38, paddingBottom: 38 }}>Login</h1>
            <Form>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control type="email" placeholder="Enter email" />
                <Form.Text className="text-muted"></Form.Text>
              </Form.Group>

              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" />
              </Form.Group>
              <Form.Group
                className="mb-3"
                controlId="formBasicCheckbox"
              ></Form.Group>
              <Stack className="mx-auto" direction="horizontal" gap={1}>
                <div className="ms-auto" style={{ paddingBottom: 12 }}>
                  <a href="../Pages/ForgotPassword.js">Forgot password?</a>
                </div>
              </Stack>

              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Form>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Login;
