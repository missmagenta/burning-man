import event from './modules/eventManagement';
import { createStore } from 'vuex'

const store = createStore({
    modules: {
        event,
    }
})

export default store;