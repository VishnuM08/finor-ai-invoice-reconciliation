export default function BankTxnTable({ txns }) {
  return (
    <div
      style={{
        marginTop: 14,
        background: "white",
        border: "1px solid #e5e7eb",
        borderRadius: 14,
        overflow: "hidden",
      }}
    >
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr style={{ background: "#f3f4f6", textAlign: "left" }}>
            <th style={{ padding: 12 }}>Date</th>
            <th style={{ padding: 12 }}>Description</th>
            <th style={{ padding: 12 }}>Amount</th>
            <th style={{ padding: 12 }}>Reconciled</th>
            <th style={{ padding: 12 }}>Matched Invoice</th>
          </tr>
        </thead>
        <tbody>
          {txns?.map((t) => (
            <tr key={t.id} style={{ borderTop: "1px solid #e5e7eb" }}>
              <td style={{ padding: 12 }}>{t.txnDate || "-"}</td>
              <td style={{ padding: 12 }}>{t.description || "-"}</td>
              <td style={{ padding: 12 }}>{t.amount ?? "-"}</td>
              <td style={{ padding: 12 }}>
                {t.reconciled ? "✅ Yes" : "— No"}
              </td>
              <td style={{ padding: 12 }}>{t.matchedInvoiceNumber || "-"}</td>
            </tr>
          ))}
          {!txns?.length && (
            <tr>
              <td colSpan={5} style={{ padding: 18, color: "#6b7280" }}>
                No bank transactions loaded yet.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
