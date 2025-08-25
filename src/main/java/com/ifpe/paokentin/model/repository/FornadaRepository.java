package com.ifpe.paokentin.model.repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifpe.paokentin.model.entity.Fornada;

@Repository
public class FornadaRepository {

	public List<Fornada> listarTodas() throws SQLException {
		List<Fornada> fornadas = new ArrayList<>();
		String sql = "SELECT id, paoid, horainicio FROM fornada";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Fornada f = new Fornada();
				f.setId(rs.getInt("id"));
				f.setPaoId(rs.getInt("paoid"));
				// Converte diretamente para LocalDateTime
				Timestamp ts = rs.getTimestamp("horainicio");
				if (ts != null) {
					f.setHoraInicio(ts.toLocalDateTime());
				}
				fornadas.add(f);
			}
		}
		return fornadas;
	}

	public Fornada buscarPorId(int id) throws SQLException {
		String sql = "SELECT id, paoid, horainicio FROM fornada WHERE id = ?";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Fornada f = new Fornada();
					f.setId(rs.getInt("id"));
					f.setPaoId(rs.getInt("paoid"));
					Timestamp ts = rs.getTimestamp("horainicio");
					if (ts != null) {
						f.setHoraInicio(ts.toLocalDateTime());
					}
					return f;
				}
			}
		}
		return null;
	}

	public void salvar(Fornada fornada) throws SQLException {
		String sql = "INSERT INTO fornada (paoid, horainicio) VALUES (?, ?)";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, fornada.getPaoId());
			stmt.setTimestamp(2, Timestamp.valueOf(fornada.getHoraInicio()));
			stmt.executeUpdate();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					fornada.setId(rs.getInt(1));
				}
			}
		}
	}

	public void atualizar(int id, Fornada fornada) throws SQLException {
		String sql = "UPDATE fornada SET paoid = ?, horainicio = ? WHERE id = ?";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, fornada.getPaoId());
			stmt.setTimestamp(2, Timestamp.valueOf(fornada.getHoraInicio()));
			stmt.setInt(3, id);
			stmt.executeUpdate();
		}
	}
}