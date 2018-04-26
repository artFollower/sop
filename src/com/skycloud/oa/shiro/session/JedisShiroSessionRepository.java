package com.skycloud.oa.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import com.skycloud.oa.shiro.JedisManager;
import com.skycloud.oa.utils.SerializeUtil;

public class JedisShiroSessionRepository extends JedisManager implements ShiroSessionRepository
{
	/**
	 * redis session key前缀
	 */
	private final String REDIS_SHIRO_SESSION = "shiro-session:";


	@Override
	public void saveSession(Session session)
	{
		if (session == null || session.getId() == null) {
			return;
		}
		byte[] key = SerializeUtil
				.serialize(getRedisSessionKey(session.getId()));
		byte[] value = SerializeUtil.serialize(session);
		Jedis jedis = this.getJedis();
		try {
			Long timeOut = session.getTimeout() / 1000;
			jedis.set(key, value);
			jedis.expire(key, Integer.parseInt(timeOut.toString()));
		} catch (JedisException e) {
		} finally {
			this.returnResource(jedis, false);
		}
	}

	@Override
	public void deleteSession(Serializable id)
	{
		if (id == null) {
			return;
		}
		Jedis jedis = this.getJedis();
		try {
			jedis.del(SerializeUtil.serialize(getRedisSessionKey(id)));
		} catch (JedisException e) {
		} finally {
			this.returnResource(jedis, false);
		}
	}

	@Override
	public Session getSession(Serializable id)
	{
		if (id == null) {
			return null;
		}
		Session session = null;
		Jedis jedis = this.getJedis();
		try {
			byte[] value = jedis.get(SerializeUtil
					.serialize(getRedisSessionKey(id)));
			session = SerializeUtil.deserialize(value, Session.class);
		} catch (JedisException e) {
		} finally {
			this.returnResource(jedis, false);
		}
		return session;
	}

	@Override
	public Collection<Session> getAllSessions()
	{
		Jedis jedis = this.getJedis();
		Set<Session> sessions = new HashSet<Session>();
		try {
			Set<byte[]> byteKeys = jedis.keys(SerializeUtil
					.serialize(this.REDIS_SHIRO_SESSION + "*"));
			if (byteKeys != null && byteKeys.size() > 0) {
				for (byte[] bs : byteKeys) {
					Session s = SerializeUtil.deserialize(jedis.get(bs),
							Session.class);
					sessions.add(s);
				}
			}
		} catch (JedisException e) {
		} finally {
			this.returnResource(jedis, false);
		}
		return sessions;
	}

	/**
	 * 获取redis中的session key
	 * 
	 * @param sessionId
	 * @return
	 */
	private String getRedisSessionKey(Serializable sessionId)
	{
		return this.REDIS_SHIRO_SESSION + sessionId;
	}
}