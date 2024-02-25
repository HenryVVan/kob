export default {
    state: {
        status: "matching", // matching表示匹配界面，playing表示对战界面
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        gamemap: null,
    },
    getters: {
    },
    // 这里的函数要用commit
    mutations: {
        updateStatus(state, status) {
            state.status = status;
        },
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_photo = opponent.photo;
            state.opponent_username = opponent.username;
        },
        updateGamemap(state, gamemap) {
            state.gamemap = gamemap;
        }

    },
    // 这里的要用dispatch
    actions: {
    },
    modules: {
    }
}