
export const ApiClient = {
    async createGame() {
        const resp =  await fetch(BACKEND_URL + `/game`, { method: "POST" })

        return resp.ok ? resp.text() : null
    },

    async joinGame(gameId) {
        const resp = await fetch(BACKEND_URL + `/game/${gameId}/join`, { method: "PATCH" });

        return resp.ok ? resp.text() : null
    },

    async fetchGameState(gameId, token) {
        const resp = await fetch(BACKEND_URL + `/game/${gameId}/state`, {
            method: "GET",
            headers: new Headers({
                "Authorization": token
            })
        })

        return resp.ok ? resp.json() : null
    },


    async makeMove(gameId, token, from, to) {
        const resp = await fetch(BACKEND_URL + `/game/${gameId}/move`, {
            method: "POST",
            headers: new Headers({
                "Authorization": token,
                "Content-Type": "application/json"
            }),
            body: JSON.stringify({
                from: { x: from.rowNum, y: from.colNum },
                to: { x: to.rowNum, y: to.colNum }
            })
        })

        if(!resp.ok) console.log(await resp.json())
    },

    async fetchAuthTicket(token) {
        const resp = await fetch(BACKEND_URL + `/game/ticket`, {
            method: "POST",
            headers: new Headers({
                "Authorization": token,
                "Content-Type": "application/json"
            })
        })

        return resp.text()
    },

    async getEventsWebSocket(token) {
        const ticket = await this.fetchAuthTicket(token)

        return new WebSocket(BACKEND_WS_URL + `/game/events?auth_ticket=${ticket}`);
    }

};

export default ApiClient;