import Index from "@/components/Index";
import Main from "@/components/Main";
import UserPage from "@/components/User";
import NotFoundError from "@/components/Error";
import { createRouter, createWebHistory } from 'vue-router'
import ParticipantTicketReg from "@/components/ParticipantTicketReg";
import ParticipantApplication from "@/components/ParticipantApplication";
import ProcessingTicketReg from "@/components/ProcessingTicketReg";
import ProcessingSales from "@/components/ProcessingSales";
import AssignForm from "@/components/pcomponents/blocks/AssignForm";

const routes = [
    {
        path: '/',
        name: 'index-page',
        component: Index
    },
    {
        path: '/auth',
        name: 'auth-page',
        component: Index
    },
    {
        path: '/main-org',
        name: 'main-org-page',
        component: Main,
        beforeEnter: (to, from, next) => {
            // if (localStorage.getItem("token") !== null) {
            //     if (localStorage.getItem("role") == 0) {
                    next();
                // } else if (localStorage.getItem("role") == 1) {
                //     next({ name: 'proccessing-ticket-reg-page' });
                // } else if (localStorage.getItem("role") == 5) {
                //     next( { name: 'user-page' });
                // }
            // } else next({ name: 'auth-page' });
        }
    },
    {
        path: '/user-page',
        name: 'user-page',
        component: UserPage,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem("role") == 5) {
                next()
            } else if (localStorage.getItem("role") == 1) {
                next({name: 'sales-org-page'});
            } else if (localStorage.getItem("role") == 0) {
                next({name: 'main-org-page'});
            } else {
                next()
            }
        }
    },
    {
        path:'/registration',
        name: 'reg-form-page',
        component: ParticipantTicketReg
    },
    {
        path:'/apply',
        name: 'apply-page',
        component: ParticipantApplication
    },
    {
        path:'/event-registration',
        name: 'sales-org-page',
        component: ProcessingTicketReg
    },
    {
        path:'/event-sales',
        name: 'proccessing-sales',
        component: ProcessingSales
    },
    {
        path:'/assign',
        name:'assign-page',
        component: AssignForm
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'error-page',
        component: NotFoundError,
        props: {
            errorCode: "404",
            errorMessage: "Данной страницы не существует"
        }
    }

];


const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

export default router;