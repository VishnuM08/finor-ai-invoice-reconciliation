import { reconcileInvoice, api } from "../api/api";

export default function InvoiceTable({ invoices, onRefresh }) {
  const handleReconcile = async (id) => {
    try {
      await reconcileInvoice(id);
      alert("Reconciliation completed ✅");
      onRefresh();
    } catch (err) {
      console.error(err);
      alert("Reconcile failed ❌. Check backend logs.");
    }
  };

  const handleExtract = async (id) => {
    try {
      await api.post(`/api/invoices/${id}/extract`);
      onRefresh();
    } catch (err) {
      console.error(err);
      alert("Extract failed ❌");
    }
  };

  const handleGlMap = async (id) => {
    try {
      await api.post(`/api/invoices/${id}/gl-map`);
      onRefresh();
    } catch (err) {
      console.error(err);
      alert("GL Mapping failed ❌");
    }
  };

  return (
    <div style={{ background: "#fff", borderRadius: 12, padding: 14 }}>
      <div style={{ fontWeight: 900, fontSize: 16, marginBottom: 10 }}>
        Invoices
      </div>

      <table width="100%" cellPadding="10" style={{ borderCollapse: "collapse" }}>
        <thead>
          <tr style={{ textAlign: "left", borderBottom: "1px solid #e5e7eb" }}>
            <th>ID</th>
            <th>Vendor</th>
            <th>Invoice #</th>
            <th>Total</th>
            <th>Status</th>
            <th>GL</th>
            <th>Actions</th>
            <th>Matched Txn</th>

          </tr>
        </thead>

        <tbody>
          {invoices?.map((inv) => (
            <tr key={inv.id} style={{ borderBottom: "1px solid #f3f4f6" }}>
              <td>{inv.id}</td>
              <td>{inv.vendorName ?? "-"}</td>
              <td>{inv.invoiceNumber ?? "-"}</td>
              <td>{inv.totalAmount ?? "-"}</td>
              <td style={{ fontWeight: 700 }}>{inv.status}</td>
              <td>
                {inv.glCode ? `${inv.glCode} - ${inv.glName}` : "-"}
              </td>

              <td style={{ display: "flex", gap: 8 }}>
                
                <button onClick={() => handleExtract(inv.id)}>Extract</button>
                <button onClick={() => handleGlMap(inv.id)}>GL Map</button>
                <button
                  onClick={() => handleReconcile(inv.id)}
                  disabled={inv.status !== "MAPPED"}
                  title={
                    inv.status !== "MAPPED"
                      ? "Complete GL mapping first"
                      : "Reconcile invoice"
                  }
                >
                  Reconcile
                </button>
              </td>
              <td>
 {inv.matchedTxnId
    ? `#${inv.matchedTxnId} | ${inv.matchedTxnAmount} | ${inv.matchedTxnDate}`
    : "-"}
</td>

            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
