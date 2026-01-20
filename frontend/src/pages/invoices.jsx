import { useEffect, useState } from "react";
import { fetchInvoices, api } from "../api/api";
import UploadInvoice from "../components/UploadInvoice";
import InvoiceTable from "../components/InvoiceTable";
import UploadBankCsv from "../components/UploadBankCsv";
import BankTxnTable from "../components/BankTxnTable";

export default function Invoices() {
  const [invoices, setInvoices] = useState([]);
  const [txns, setTxns] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadInvoices = async () => {
    const res = await fetchInvoices();
    setInvoices(res.data);
  };

  const loadTxns = async () => {
    const res = await api.get("/api/bank/transactions");
    setTxns(res.data);
  };

  const loadAll = async () => {
    try {
      setLoading(true);
      await Promise.all([loadInvoices(), loadTxns()]);
    } catch (e) {
      console.error(e);
      alert("Failed to load data. Is backend running?");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadAll();
  }, []);

  return (
    <div>
      <div style={{ fontSize: 22, fontWeight: 900, marginBottom: 10 }}>
        Invoice Pipeline
      </div>

      <UploadInvoice onUploaded={loadAll} />

      <UploadBankCsv onUploaded={loadAll} />

      <button
        onClick={async () => {
          try {
            const res = await api.post("/api/invoices/reconcile-all");
            alert(res.data);
            loadAll();
          } catch (e) {
            console.error(e);
            alert("Reconcile All failed ❌");
          }
        }}
        style={{
          padding: "10px 14px",
          background: "black",
          color: "white",
          borderRadius: 10,
          border: "none",
          cursor: "pointer",
          fontWeight: 800,
          marginBottom: 15,
        }}
      >
        Reconcile All
      </button>

      {loading ? (
        <div style={{ padding: 14, color: "#6b7280" }}>Loading...</div>
      ) : (
        <>
          <InvoiceTable invoices={invoices} onRefresh={loadAll} />

          <div style={{ marginTop: 24, fontSize: 18, fontWeight: 900 }}>
            Bank Transactions
          </div>

          <div style={{ color: "#6b7280", marginTop: 4, fontSize: 13 }}>
            Used for reconciliation matching (amount + vendor keyword)
          </div>

          <BankTxnTable txns={txns} />
        </>
      )}
    </div>
  );
}
