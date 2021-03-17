import React, {useState, useEffect} from 'react';
import './App.css'
import userEvent from "@testing-library/user-event";

function App() {

    const[response, setResponse] = useState();

    function xmlConnnect(url, method, callback) {
        let con = new XMLHttpRequest();
        con.open(method, "http://localhost" + url);
        con.onreadystatechange = function () {
            if (con.readyState === XMLHttpRequest.DONE) {
                if (con.status === 200) {
                    let json = JSON.parse(con.responseText);
                    setResponse(json.data[0].user_name);
                }
            }
        }
        con.send();
    }

    useEffect(() => {
        xmlConnnect("/livestreams", "get");
    });

    return (
        <div className="App">
            <header className="App-header">
                <p>hello</p>
                <p>{response}</p>
            </header>
        </div>
    );
}

export default App;
