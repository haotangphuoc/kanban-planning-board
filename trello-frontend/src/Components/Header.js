import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";

const Header = () => {
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Container>
        <Navbar.Brand href="/">Trello-Clone</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link>Create</Nav.Link>
            <Nav.Link>Item</Nav.Link>
            <Nav.Link>Item</Nav.Link>
          </Nav>
          <Nav>
            <Nav.Link href="../Pages/Login.js">Login</Nav.Link>
            <Nav.Link href="../Pages/Register.js"> Register</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
