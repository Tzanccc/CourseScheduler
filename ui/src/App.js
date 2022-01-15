import React, { Component } from 'react';

import './App.css';
import Navbar from './components/navbar';
import Counters from "./components/counters"

class App extends React.Component {

    state = {
        counters: [
            { id: 1, value: 4 },
            { id: 2, value: 0 },
            { id: 3, value: 0 },
            { id: 4, value: 0 }
        ]
    }

    constructor() {
        super();
        this.handleDelete = this.handleDelete.bind(this);
        this.handleReset = this.handleReset.bind(this);
        this.handleIncrement = this.handleIncrement.bind(this);
    }

    handleIncrement(counter) {
        const counters = [...this.state.counters]; // spead operator?
        const index = counters.indexOf(counter);
        counters[index] = { ...counter };
        counters[index].value++;
        this.setState({ counters });
    }

    handleDelete(counterID) {
        this.setState({ counters: this.state.counters.filter(c => c.id !== counterID) });
    }

    handleReset() {
        const counters = this.state.counters.map(c => {
            c.value = 0;
            return c;
        });
        this.setState({ counters });
    }

    render() {
        return <>
            <Navbar totalCounters={this.state.counters.filter(c => c.value > 0).length} />
            <main className='container'>
                <Counters onReset={this.handleReset} onIncrement={this.handleIncrement} onDelete={this.handleDelete} counters={this.state.counters} />
            </main>
        </>;
    }
}

export default App;
