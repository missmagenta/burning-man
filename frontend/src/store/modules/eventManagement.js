import axios from 'axios';

const state = () => ({
    role: localStorage.getItem('role') || -1,
    jwtToken: localStorage.getItem('token') || '',
    person_id: localStorage.getItem('person_id') || 0,
    person_name: localStorage.getItem('person_name') || "",
    ome_id: localStorage.getItem('ome_id') || null,


    l_you: {  event_archive_record: localStorage.getItem("event_archive_record") || null},
    event_content: [],
    event_archive_data: [],
    event_id: localStorage.getItem('event_id') || 1,


    tickets_data: [],
    event_reg_process_id: localStorage.getItem("event_reg_process_id") || null,
    event_sales_process_id: localStorage.getItem("event_sales_process_id") || null,

    organizer_id: localStorage.getItem('organizer_id') || 0,
    alive_orgs_data: [],


    future_events_data: [],
    future_event_id: 39,
    ome_data: [],


    event_reg_table_data: [],
    event_sales_table_data: [],


    applications_table_data: [],
    members_table_data: [],
    main_themes_data: [],


    queue_reg_for_sales_manager: [],
    queue_purchase_for_sales_manager: [],

    queue_reg_for_user: [],
    queue_purchase_for_user: [],
});

const getters = {
    CUR_ARCHIVE_RECORD: state => {
        return state.l_you.event_archive_record;
    },
    L_YOU: state => {
        return state.l_you;
    },
    FUTURE_EVENT: state => {
        return state.future_event_id;
    },
    CUR_PERSON: state => {
        return state.person_id;
    },
    CUR_OME: state => {
        return state.ome_id;
    }
};

const mutations = {
    SET_INITIAL_INF: (state, payload) => {
        state.person_id = payload.personId;
        localStorage.setItem("person_id", payload.personId);
        state.person_name = payload.personName;
        localStorage.setItem("person_name", payload.personName);
        state.ome_id = payload.omeId;
        localStorage.setItem("ome_id", payload.omeId);
    },
    SET_TOKEN: (state, payload) => {
        localStorage.setItem("jwtToken", payload);
        state.jwtToken = payload;
    },

    SET_ROLE: (state, payload) => {
        switch (payload) {
            case 'MAIN_IDEA_MANAGER':
                localStorage.setItem("role", 0);
                state.role = 0;
                break
            case 'SALES_MANAGER':
                localStorage.setItem("role", 1);
                state.role = 1;
                break
            case 'ART_MANAGER':
                localStorage.setItem("role", 2);
                state.role = 2;
                break
            case 'BURNING_MANAGER':
                localStorage.setItem("role", 3);
                state.role = 3;
                break
            case 'PARTICIPANT':
                localStorage.setItem("role", 4);
                state.role = 4;
                break
            default:
                localStorage.setItem("role", 5);
                state.role = 5;
                break
        }
    },
    SET_EVENT_CONTENT_DATA: (state, payload) => {
        state.event_content = payload;
    },

    SET_EVENT_REG_TABLE_DATA: (state, payload) => {
        state.event_reg_table_data = payload;
    },
    SET_EVENT_SALES_ID: (state, payload) => {
        state.event_sales_process_id = payload;
    },
    SET_EVENT_SALES_TABLE_DATA: (state, payload) => {
        state.event_sales_table_data = payload;
    },
    SET_QUEUE_REG_FOR_SALES_MANAGER: (state, payload) => {
        state.queue_reg_for_sales_manager = payload;
    },
    SET_QUEUE_PURCHASE_FOR_SALES_MANAGER: (state, payload) => {
        state.queue_purchase_for_sales_manager = payload;
    },

    SET_APPLICATIONS_TABLE_DATA: (state, payload) => {
        state.applications_table_data = payload;
    },
    SET_ARCHIVE_DATA: (state, payload) => {
        state.event_archive_data = payload;
    },
    SET_FUCK_YOU: (state, payload) => {
        state.l_you = payload;
        localStorage.setItem("event_archive_record", payload.id)
    },
    SET_PART_REG_TABLE_DATA: (state, payload) => {
        state.queue_reg_for_user = payload;
    },
    SET_PART_PUR_TABLE_DATA: (state, payload) => {
        state.queue_purchase_for_user = payload;
    },
    SET_TICKETS_DATA: (state, payload) => {
        state.tickets_data = payload;
    },
    SET_REG_PROCESS_ID: (state, payload) => {
        state.event_reg_process_id = payload;
    },
    SET_FUTURE_EVENTS_DATA: (state, payload) => {
        state.future_events_data = payload;
    },
    SET_FUTURE_EVENT_ID: (state, payload) => {
        state.future_event_id = payload;
    },
    SET_ALIVE_ORG_DATA: (state, payload) => {
        state.alive_orgs_data = payload;
    },
    SET_OME_DATA: (state, payload) => {
        state.ome_data = payload;
    },
    SET_FUTURE_OME_ID: (state, payload) => {
        state.ome_id = payload;
    }

};

const actions = {
    //json в формате username, password, name, surname, email, contactNumber, birth_date, person_gender -> jwt token, role, id_person, person name
    CREATE_NEW_ACCOUNT(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/auth/signup', data: payload, method: 'POST', headers: { "Authorization": "Bearer", "Content-type": "application/json" } })
                .then(resp => {
                    if (resp.status == 200) {
                        context.commit('SET_TOKEN', resp.data.data.token);
                        context.commit('SET_ROLE', resp.data.data.role);
                        context.commit('SET_INITIAL_INF', resp.data.data);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })

    },

    LOG_IN_ACCOUNT(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/auth/signin', data: payload, method: 'POST' })
                .then(resp => {
                    if (resp.status == 200) {
                        context.commit('SET_TOKEN', resp.data.data.token);
                        context.commit('SET_ROLE', resp.data.data.role);
                        context.commit('SET_INITIAL_INF', resp.data.data);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    SELECT_EVENT(context, payload) {
        const helper = context.getters.L_YOU;
        helper.event_archive_record = payload.id;
        context.commit('SET_FUCK_YOU', helper);
    },

    // ничего на вход -> массив архивных записей
    GET_ALL_ARCHIVE_RECORDS(context) {
        return new Promise((resolve, reject) => {
            axios({ url: '/archive-records/', method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_ARCHIVE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // на вход id archive record ->  вернуть массив всех ивентов
    GET_EVENT_CONTENT(context, payload) {
        let archive_record_id = context.getters.CUR_ARCHIVE_RECORD;
        if ( archive_record_id == 'undefined' || archive_record_id == null || archive_record_id === null) {
            archive_record_id = 1;
        }
        return new Promise((resolve, reject) => {
            axios({ url: '/archive-records/contents/' + archive_record_id, data: payload, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_CONTENT_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // person_id -> все его регистрации на все события в течение разных лет
    GET_MY_REGISTRATIONS(context) {
        return new Promise((resolve, reject) => {
            const person_id = context.getters.CUR_PERSON;
            axios({ url: '/registration/records/' + person_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_PART_REG_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // person_id -> все его билеты на все события в течение разных лет
    GET_MY_TICKETS(context) {
        return new Promise((resolve, reject) => {
            const person_id = context.getters.CUR_PERSON;
            axios({ url: '/purchase/tickets/' + person_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_PART_PUR_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })

    },

    // ничего не вход -> вернуть типы билетов
    GET_ALL_TICKETS(context) {
        return new Promise((resolve, reject) => {
            axios({ url: '/registration/', method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_TICKETS_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },
    // на вход id типа билета
    REGISTER(context, payload) {
        return new Promise((resolve, reject) => {
            payload["personId"] = context.getters.CUR_PERSON;
            axios({ url: '/registration/register', data: payload, method: 'POST' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },
    // на вход id типа билета, кол-во билетов, паспорт
    BUY(context, payload) {
        return new Promise((resolve, reject) =>{
            payload["personId"] = context.getters.CUR_PERSON;
            axios({ url: '/purchase/buy', data:payload, method: 'POST' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })

    },

    GET_ALL_REG_PROCESSES(context, payload) {
        return new Promise((resolve, reject) => {
            const event_id = payload.eventId;
            axios({ url: '/reg/types/' + event_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_REG_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    GET_ALL_REG_PROCESSES_FUTURE(context) {
        return new Promise((resolve, reject) => {
            const event_id = context.getters.FUTURE_EVENT;
            axios({ url: '/reg/types/' + event_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_REG_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    GET_ALL_REG_PROCESSES_ORG(context) {
        return new Promise((resolve, reject) => {
            const org_id = context.getters.CUR_PERSON;
            axios({ url: '/reg/org-types/' + org_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        if ((resp.data !== undefined && resp.data.collection &&
                            resp.data.collection.length > 0 && resp.data.collection[1] !== undefined)) {
                            context.commit('SET_REG_PROCESS_ID', resp.data.collection[1].id);
                        }
                        if ((resp. data.collection[0]) != undefined) {
                            context.commit('SET_FUTURE_OME_ID', resp. data.collection[0].eventManagementProcessId)
                        }
                        context.commit('SET_EVENT_REG_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // event id -> all sales per event
    GET_ALL_SALES_PROCESSES(context, payload) {
        return new Promise((resolve, reject) => {
            const event_id = payload.eventId;
            axios({ url: '/sales/event/' + event_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_SALES_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },
    GET_ALL_SALES_PROCESSES_FUTURE(context) {
        return new Promise((resolve, reject) => {
            const event_id = context.getters.FUTURE_EVENT;
            axios({ url: '/sales/event/' + event_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_SALES_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    GET_ALL_SALES_PROCESSES_ORG(context) {
        const org_id = context.getters.CUR_PERSON;
        return new Promise((resolve, reject) => {
            axios({ url: '/sales/types/' + org_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_SALES_TABLE_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // ничего на вход -> массив будущих фестивалей
    GET_ALL_FUTURE_EVENTS(context) {
        return new Promise((resolve, reject) => {
            axios({ url: '/future/', method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_FUTURE_EVENTS_DATA', resp.data.collection);
                        context.commit('SET_FUTURE_EVENT_ID', resp.data.collection[0].id);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    UPDATE_EVENT_STATUS(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/event-management/update-status',
                data: payload,
                method: 'POST'})
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // ничего на вход -> массив живых людей с должностью орг
    GET_ALL_ALIVE_ORGS(context) {
        return new Promise((resolve, reject) => {
            axios({ url: '/organizers/', method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_ALIVE_ORG_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // кликаем и выбираем event_id, org_id -> ничего не возвращаем
    ASSIGN_ORGANIZER_TO_EVENT(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/event-management/assign',
                data: payload,
                method: 'POST'})
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // на вход ничего -> вернуть назначенных оргов будущих событий
    GET_OME(context) {
        return new Promise((resolve, reject) => {
            axios({ url: '/event-management/', method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_OME_DATA', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // regId на вход
    SEE_REGISTERED_PEOPLE(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/registration/see', data: payload, method: 'POST' })
                .then(resp => {
                    if (resp.status == 200) {
                        context.commit('SET_QUEUE_REG_FOR_SALES_MANAGER', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // на вход id типа билета, price
    PUBLISH_TICKET_REG(context, payload) {
        payload["omeId"] = context.getters.CUR_OME;
        return new Promise((resolve, reject) => {
            axios({ url: '/reg/start', data: payload, method: 'POST' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_REG_TABLE_DATA', resp.data.data);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // на вход reg id, количество доступных билетов
    PUBLISH_TICKET_SALES(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/sales/start', data: payload, method: 'POST' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_EVENT_SALES_ID', resp.data.data);
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // sales id на вход -> ничего не возвращаем
    FINISH_SALES_PROCESS(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/sales/finish', data: payload, method: 'POST' })
                .then(resp => {
                    if (resp.status == 200) {
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // purchase id на вход
    SEE_PARTICIPANTS(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/purchase/see', data: payload, method: 'POST' })
                .then(resp => {
                    if (resp.status == 200) {
                        context.commit('SET_QUEUE_PURCHASE_FOR_SALES_MANAGER', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },


    // event id на вход
    SEE_PARTICIPANTS_EVENT(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/purchase/participants', data: payload, method: 'POST' })
                .then(resp => {
                    if (resp.status == 200) {
                        context.commit('SET_QUEUE_PURCHASE_FOR_SALES_MANAGER', resp.data.collection);
                        resolve(resp);
                    } else {
                        let err = {message: resp.message};
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // payload содержит заполненные данные с формы, вернуть поданную заявку (заявки)
    APPLY_FOR_ART_COMPETITION(context, payload) {
        payload["eventId"] = context.getters.FUTURE_EVENT;
        payload["participantId"] = context.getters.CUR_PERSON;
        return new Promise((resolve, reject) => {
            axios({ url: '/applications/apply', data: payload, method: 'POST' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        context.commit('SET_APPLICATIONS_TABLE_DATA', resp.data.collection);

                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // на вход person id -> вернуть мои заявки
    GET_MY_APPLICATIONS(context) {
        return new Promise((resolve, reject) => {
            const person_id = context.getters.CUR_PERSON;
            axios({ url: '/applications/records/' + person_id, method: 'GET' })
                .then(resp => {
                    console.log(resp);
                    if (resp.status == 200) {
                        if (resp.data.collection.length > 0) {
                            context.commit('SET_APPLICATIONS_TABLE_DATA', resp.data.collection);
                            context.commit('SET_FUTURE_OME_ID', resp.data.collection[0].id);
                        }
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },

    // reg id на вход -> ничего не возвращаем
    FINISH_REG_PROCESS(context, payload) {
        return new Promise((resolve, reject) => {
            axios({ url: '/reg/finish', data: payload, method: 'POST' })
                .then(resp => {
                    if (resp.status == 200) {
                        resolve(resp);
                    } else {
                        let err = { message: resp.message };
                        reject(err);
                    }
                })
                .catch(error => {
                    let err = error.response.data;
                    reject(err);
                })
        })
    },




};
export default {
    state,
    getters,
    mutations,
    actions,
};