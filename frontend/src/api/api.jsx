import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

export const api = axios.create({
  baseURL: BASE_URL,
});

export const fetchReconciliationSummary = () =>
  api.get("/api/reports/reconciliation-summary");

export const fetchCategorySummary = () =>
  api.get("/api/reports/category-summary");

export const fetchVendorSummary = () =>
  api.get("/api/reports/vendor-summary");

export const fetchMonthlySummary = () =>
  api.get("/api/reports/monthly-summary");
export const fetchInvoices = () => api.get("/api/invoices");

export const fetchVendors = () => api.get("/api/vendors");

export const reconcileInvoice = (id) =>
  api.post(`/api/invoices/${id}/reconcile`);
