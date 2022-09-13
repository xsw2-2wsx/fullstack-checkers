import React, { useState } from 'react';
import { Link } from "react-router-dom";
import ApiClient from './ApiClient';

export const Home = () => {

    const [gameId, setGameId] = useState();

    const createGame = async () => {
        setGameId(await ApiClient.createGame())
    }

    console.log(APP_URL);

    const createLink = () => <Link to={`/join/${gameId}`}>{`Join game with: ${APP_URL}/play/${gameId}`}</Link>

    return (
        <>
            <h1>Online checkers</h1>
            <button onClick={createGame} className="btn btn-danger">Create a new game</button> <br/>
            
            { gameId && <p>{createLink()}</p> }
                     
        </>
    )
}

export default Home;