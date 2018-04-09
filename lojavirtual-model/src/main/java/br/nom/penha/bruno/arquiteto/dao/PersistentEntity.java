package br.nom.penha.bruno.arquiteto.dao;

import java.io.Serializable;

/**
 * Interface que deve ser implemenada por todas as entidades persistentes de negócio.
 * 
 * A classe DaoSupport utiliza o método isNew para verificar se deve ser feita
 * uma inserção ou atualização no banco de dados.
 * 
 *
 * @param <T> tipo da chave primária da entidade
 */
public interface PersistentEntity<T extends Serializable> extends Serializable{
	
	/**
	 * Método que retorna a chave primária de uma entidade persistente. Foi criado
	 * para permitir que componentes de persistência tenham acesso ao valor da chave
	 * primária sem a necessidade de conhecer qual o nome do campo utilizado para 
	 * chamar o método get adequado.
	 * 
	 * @return chave primária da entidade
	 */
	public T getId();
	
	/**
	 * Método que permite aos componentes de persistência verificarem se uma 
	 * determinada entidade já foi inserida em um banco de dados ou não.
	 * 
	 * @return true se a entidade for nova, ou seja, ainda não foi inserida em um banco de dados
	 */
	public boolean isNew();
}
