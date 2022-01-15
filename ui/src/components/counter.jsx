import { getByPlaceholderText } from '@testing-library/react';
import React, { Component } from 'react';

class Counter extends React.Component {
    constructor(props) {
        super(props);
    }

    render() { 
        console.log(this.props.counter);

        return <div>
            <span className={this.getBadgeClasses()}>{this.formatCount()}</span>
            <button onClick={() => this.props.onIncrement(this.props.counter)} className="btn btn-secondary btn-sm">Increment</button>
            <button onClick={() => this.props.onDelete(this.props.counter.id)} className='btn btn-danger btn-small m-2'>Delete</button>
        </div>;
    }

    getBadgeClasses() {
        let classes = "badge m-2 bg-";
        classes += (this.props.counter.value === 0) ? "warning" : "primary";
        return classes;
    }

    formatCount() {
        return this.props.counter.value === 0 ? "Zero" : this.props.counter.value;
    }
}

export default Counter;