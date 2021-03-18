import React from 'react';
import './App.css'

function test(text) {
    console.log("hello world");
    return (
        <p>{text}</p>
    );
}


function HttpConnect(props) {
    let con = new XMLHttpRequest();

    function callback() {
        if (con.readyState === XMLHttpRequest.DONE) {
            if (con.status === 200) {
                test(con.responseText);
            }
        }
    }

    con.onreadystatechange = callback;
    con.open(props.method, "http://localhost" + props.url);
    con.send();

    return (
        <div>result</div>
    );
}

function App() {
    return (
        <HttpConnect url="/livestreams/ko/100" method="get"></HttpConnect>
    );
}

export default App;
