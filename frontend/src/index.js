import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes,  Route } from "react-router-dom";
import Home from './Home';
import Game from './game/Game';
import { JoinGame } from './JoinGame';
import Board from './game/board/Board';
import './style.scss'
import bootstrap from 'bootstrap';


const root = createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Home/>} />
            <Route path='join/:gameId' element={<JoinGame />}/>
            <Route path='play' element={<Game />}/>
        </Routes>
    </BrowserRouter>
);