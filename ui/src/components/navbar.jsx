import React, { Component } from 'react';

const NavBar = ({totalCounters}) => {
    return (
        <nav className="navbar navbar-light bg-light">
            <div className="container-fluid">
                <a className="navbar-brand" href="#">Navbar
                    <span className='badge bg-pill bg-secondary m-2'>{totalCounters}</span>
                </a>
            </div>
        </nav>
    );
}

export default NavBar;