import { useEffect, useState } from "react";
import {
  fetchReconciliationSummary,
  fetchCategorySummary,
  fetchVendorSummary,
  fetchMonthlySummary,
} from "../api/api";

export default function Reports() {
  const [summary, setSummary] = useState(null);
  const [category, setCategory] = useState([]);
  const [vendor, setVendor] = useState([]);
  const [monthly, setMonthly] = useState([]);
  const [loading, setLoading] = useState(true);

  const load = async () => {
    try {
      setLoading(true);
      const [s, c, v, m] = await Promise.all([
        fetchReconciliationSummary(),
        fetchCategorySummary(),
        fetchVendorSummary(),
        fetchMonthlySummary(),
      ]);

      setSummary(s.data);
      setCategory(c.data);
      setVendor(v.data);
      setMonthly(m.data);
    } catch (err) {
      console.error(err);
      alert("Reports failed to load. Check backend / CORS.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    load();
  }, []);

  if (loading) return <div style={{ color: "#6b7280" }}>Loading reports...</div>;
  if (!summary) return <div style={{ color: "#6b7280" }}>No data</div>;

  return (
    <div>
      <div style={{ fontSize: 22, fontWeight: 900, marginBottom: 12 }}>
        Reports Dashboard
      </div>

      {/* KPI Cards */}
      <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: 14 }}>
        <Kpi title="Total Invoices" value={summary.totalInvoices} />
        <Kpi title="Extracted" value={summary.extracted} />
        <Kpi title="Mapped" value={summary.mapped} />
        <Kpi title="Reconciled" value={summary.reconciled} />
        <Kpi title="Not Reconciled" value={summary.notReconciled} />
        <Kpi title="Total Spend" value={`₹ ${summary.totalSpend ?? 0}`} />
      </div>

      <Section title="Category Summary">
        <SimpleTable
          headers={["Category", "Invoices", "Total Amount"]}
          rows={category.map((x) => [
            x.category ?? "-",
            x.count ?? 0,
            x.totalAmount ?? 0,
          ])}
        />
      </Section>

      <Section title="Vendor Summary">
        <SimpleTable
          headers={["Vendor", "Invoices", "Total Amount"]}
          rows={vendor.map((x) => [x.vendor ?? "-", x.count ?? 0, x.totalAmount ?? 0])}
        />
      </Section>

      <Section title="Monthly Summary">
        <SimpleTable
          headers={["Month", "Total Amount"]}
          rows={monthly.map((x) => [x.month ?? "-", x.totalAmount ?? 0])}
        />
      </Section>
    </div>
  );
}

/* ---------- UI Helpers ---------- */

function Kpi({ title, value }) {
  return (
    <div
      style={{
        background: "white",
        border: "1px solid #e5e7eb",
        borderRadius: 14,
        padding: 16,
      }}
    >
      <div style={{ fontSize: 13, color: "#6b7280", fontWeight: 700 }}>{title}</div>
      <div style={{ fontSize: 22, fontWeight: 900, marginTop: 8 }}>{value}</div>
    </div>
  );
}

function Section({ title, children }) {
  return (
    <div style={{ marginTop: 22 }}>
      <div style={{ fontSize: 18, fontWeight: 900 }}>{title}</div>
      <div style={{ marginTop: 10 }}>{children}</div>
    </div>
  );
}

function SimpleTable({ headers, rows }) {
  return (
    <div
      style={{
        background: "white",
        border: "1px solid #e5e7eb",
        borderRadius: 14,
        overflow: "hidden",
      }}
    >
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr style={{ background: "#f3f4f6", textAlign: "left" }}>
            {headers.map((h) => (
              <th key={h} style={{ padding: 12 }}>
                {h}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((r, idx) => (
            <tr key={idx} style={{ borderTop: "1px solid #e5e7eb" }}>
              {r.map((cell, i) => (
                <td key={i} style={{ padding: 12 }}>
                  {cell}
                </td>
              ))}
            </tr>
          ))}

          {!rows.length && (
            <tr>
              <td colSpan={headers.length} style={{ padding: 18, color: "#6b7280" }}>
                No data available.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
