package com.ifpe.paokentin.infrastructure.repositories;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifpe.paokentin.domain.entities.Pao;
import com.ifpe.paokentin.domain.enums.CorEnum;
import com.ifpe.paokentin.infrastructure.services.ConnectionManager;

@Repository
public class PaoRepository {

	public void salvar(Pao pao) throws SQLException {
		String sql = "INSERT INTO pao (nome, descricao, tempoPreparo, cor) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, pao.getNome());
			stmt.setString(2, pao.getDescricao());
			stmt.setLong(3, pao.getTempoPreparo());
			stmt.setString(4, pao.getCor().name());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				pao.setId(rs.getInt(1));
			}
		}
	}

	public Pao buscarPorId(int id) throws SQLException {
		String sql = "SELECT * FROM pao WHERE id = ?";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Pao pao = new Pao();
				pao.setId(rs.getInt("id"));
				pao.setNome(rs.getString("nome"));
				pao.setDescricao(rs.getString("descricao"));
				pao.setTempoPreparo(rs.getLong("tempoPreparo"));

				String corStr = rs.getString("cor");
				CorEnum cor = CorEnum.valueOf(corStr);
				pao.setCor(cor);

				return pao;
			}
			return null;
		}

	}

	public List<Pao> listarTodos() throws SQLException {
		List<Pao> lista = new ArrayList<>();
		String sql = "SELECT * FROM pao ORDER BY id ASC";

		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Pao pao = new Pao();
				pao.setId(rs.getInt("id"));
				pao.setNome(rs.getString("nome"));
				pao.setDescricao(rs.getString("descricao"));
				pao.setTempoPreparo(rs.getLong("tempoPreparo"));

				String corStr = rs.getString("cor");
				CorEnum cor = CorEnum.valueOf(corStr);
				pao.setCor(cor);

				lista.add(pao);
			}
		}
		return lista;
	}

	public void atualizar(int id, Pao pao) throws SQLException {
		String sql = "UPDATE pao SET nome = ?, descricao = ?, tempoPreparo = ?, cor = ? WHERE id = ?";
		try (Connection conn = ConnectionManager.getNewConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, pao.getNome());
			stmt.setString(2, pao.getDescricao());
			stmt.setLong(3, pao.getTempoPreparo());
			stmt.setString(4, pao.getCor().name());

			stmt.setInt(5, pao.getId());
			stmt.executeUpdate();
		}

	}
}
