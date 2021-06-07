import { createStore } from 'vuex'

export default createStore({
    state: {
        id: 0
    },
    mutations: {
        setId(state, id) {
            state.id = id
        }
    },
    getters: {
        id: state => state.id
    }
})