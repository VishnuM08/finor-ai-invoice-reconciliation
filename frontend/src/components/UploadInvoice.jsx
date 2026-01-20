import { useState } from "react";
import { api } from "../api/api";

export default function UploadInvoice({ onUploaded }) {
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);

  const upload = async () => {
    if (!file) return alert("Please select an invoice PDF");

    const form = new FormData();
    form.append("file", file);

    try {
      setLoading(true);
      await api.post("/api/invoices/upload", form, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      alert("Invoice uploaded successfully");
      setFile(null);
      onUploaded?.();
    } catch (e) {
      console.error(e);
      alert("Upload failed. Check backend logs.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      style={{
        background: "white",
        border: "1px solid #e5e7eb",
        borderRadius: 14,
        padding: 16,
        display: "flex",
        gap: 12,
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <div style={{ display: "flex", gap: 12, alignItems: "center" }}>
        <input
          type="file"
          accept=".pdf"
          onChange={(e) => setFile(e.target.files?.[0])}
        />
        <div style={{ color: "#6b7280", fontSize: 13 }}>
          Upload invoice PDF
        </div>
      </div>

      <button
        onClick={upload}
        disabled={loading}
        style={{
          padding: "10px 14px",
          borderRadius: 10,
          border: "1px solid #111827",
          background: "#111827",
          color: "white",
          fontWeight: 700,
        }}
      >
        {loading ? "Uploading..." : "Upload Invoice"}
      </button>
    </div>
  );
}
