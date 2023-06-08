import { Navbar, Container } from "react-bootstrap";

const Header = () => {
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Container>
        <Navbar.Brand href="/">Trello-Clone</Navbar.Brand>
        {/* DELETE THIS AFTER WE CONNECT TO DATABASE AND LOGIN FEATURE REDIRECTS USER */}
        <Navbar.Brand href="../Pages/Homepage.js">Homepage</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav"></Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
