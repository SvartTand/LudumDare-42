package svarttand.application.misc;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

public class ParticleHandler {
	
	private static HashMap<ParticleType, ParticleEffectPool> pools;
	private static HashMap<ParticleType, Array<PooledEffect>> activeEffectsMap;
	
	public ParticleHandler(TextureAtlas atlas) {
		pools = new HashMap<ParticleType, ParticleEffectPool>();
		activeEffectsMap = new HashMap<ParticleType, Array<PooledEffect>>();
		
		//Init effects
		for (ParticleType effectType : ParticleType.values()) {
			ParticleEffect particleEffect = new ParticleEffect();
			particleEffect.load(Gdx.files.internal(effectType.getPath()), atlas);
			
			ParticleEffectPool pool = new ParticleEffectPool(particleEffect, (int) (effectType.getPoolSIze()*0.5f), effectType.getPoolSIze());
			pools.put(effectType, pool);
			
			Array<PooledEffect> activeEffects = new Array<PooledEffect>();
			activeEffectsMap.put(effectType, activeEffects);
		}
	}
	
	public void addParticleEffect(ParticleType type, float x, float y) {
		PooledEffect effect = pools.get(type).obtain();
		if (effect != null) {
			System.out.println("yes");
			activeEffectsMap.get(type).add(effect);
			effect.setPosition(x, y);
		}
	}
	
	public void addParticleEffect(ParticleType type, float x, float y, int duration) {
		PooledEffect effect = pools.get(type).obtain();
		if (effect != null) {
			activeEffectsMap.get(type).add(effect);
			effect.setDuration(duration);
			effect.setPosition(x, y);
		}
	}
	
	public PooledEffect returnAddedParticleEffect(ParticleType type, float x, float y, int duration) {
		PooledEffect effect = pools.get(type).obtain();
		if (effect != null) {
			activeEffectsMap.get(type).add(effect);
			effect.setDuration(duration);
			effect.setPosition(x, y);
		}
		return effect;
	}
	
	public void render(SpriteBatch batch, float delta) {
		for (ParticleType key : ParticleType.values()) {
			if (key.getBehind()) continue;
			Array<PooledEffect> effects = activeEffectsMap.get(key);
			for (int i = 0; i <effects.size; i++) {
				PooledEffect effect = effects.get(i);
				if (effect.isComplete()) {
					pools.get(key).free(effect);
					effects.removeIndex(i);
				} else {
					effect.draw(batch, delta);
				}
			}
		}
	}
	
	public void renderBehind(SpriteBatch batch, float delta) {
		for (ParticleType key : ParticleType.values()) {
			if (!key.getBehind()) continue;
			Array<PooledEffect> effects = activeEffectsMap.get(key);
			for (int i = 0; i <effects.size; i++) {
				PooledEffect effect = effects.get(i);
				if (effect.isComplete()) {
					pools.get(key).free(effect);
					effects.removeIndex(i);
				} else {
					effect.draw(batch, delta);
				}
			}
		}
	}
	
	public void dispose() {
		for (ParticleType key : ParticleType.values()) {
			pools.get(key).clear();
			for (int i = 0; i < activeEffectsMap.get(key).size; i++) {
				activeEffectsMap.get(key).get(i).dispose();
			}
		}
	}

}
