/**
 * JankenGameDao.java
 *
 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */

package com.kadai.aws.repository;

import java.sql.SQLException;

import com.kadai.aws.model.GameResult;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
public interface JankenGameDao {

	void recordGameResult(GameResult gameResult) throws SQLException;

}
