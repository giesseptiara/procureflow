import { Routes } from '@angular/router';
import { Dashboard } from './features/dashboard/dashboard';
import { PurchaseRequests } from './features/purchase-requests/purchase-requests';
import { Quotations } from './features/quotations/quotations';
import { PurchaseOrders } from './features/purchase-orders/purchase-orders';
import { Deliveries } from './features/deliveries/deliveries';
import { Vendors } from './features/vendors/vendors';
import { Departments } from './features/departments/departments';
import { Login } from './pages/login/login';
import { authGuard } from './guards/auth-guard';
import { roleGuard } from './guards/role-guard';

export const routes: Routes = [
  {
    path: 'login',
    component: Login
  },
  {
    path: '',
    component: Dashboard,
    canActivate: [authGuard]
  },
  {
    path: 'purchase-requests',
    component: PurchaseRequests,
    canActivate: [
      authGuard,
      roleGuard(['REQUESTER', 'APPROVER', 'ADMIN'])
    ]
  },
  {
    path: 'quotations',
    component: Quotations,
    canActivate: [
      authGuard,
      roleGuard(['PROCUREMENT', 'ADMIN'])
    ]
  },
  {
    path: 'purchase-orders',
    component: PurchaseOrders,
    canActivate: [
      authGuard,
      roleGuard(['PROCUREMENT', 'ADMIN'])
    ]
  },
  {
    path: 'deliveries',
    component: Deliveries,
    canActivate: [
      authGuard,
      roleGuard(['WAREHOUSE', 'ADMIN'])
    ]
  },
  {
    path: 'vendors',
    component: Vendors,
    canActivate: [
      authGuard,
      roleGuard(['PROCUREMENT', 'ADMIN'])
    ]
  },
  {
    path: 'departments',
    component: Departments
  }

  
];