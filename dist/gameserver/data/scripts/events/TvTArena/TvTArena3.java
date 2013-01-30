/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package events.TvTArena;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lineage2.gameserver.Announcements;
import lineage2.gameserver.data.htm.HtmCache;
import lineage2.gameserver.data.xml.holder.NpcHolder;
import lineage2.gameserver.instancemanager.ServerVariables;
import lineage2.gameserver.listener.actor.OnDeathListener;
import lineage2.gameserver.listener.actor.player.OnPlayerExitListener;
import lineage2.gameserver.listener.actor.player.OnTeleportListener;
import lineage2.gameserver.model.Creature;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.SimpleSpawner;
import lineage2.gameserver.model.actor.listener.CharListenerList;
import lineage2.gameserver.model.entity.Reflection;
import lineage2.gameserver.model.instances.NpcInstance;
import lineage2.gameserver.scripts.Functions;
import lineage2.gameserver.scripts.ScriptFile;
import lineage2.gameserver.templates.npc.NpcTemplate;
import lineage2.gameserver.utils.Location;
import lineage2.gameserver.utils.ReflectionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TvTArena3 extends Functions implements ScriptFile, OnDeathListener, OnTeleportListener, OnPlayerExitListener
{
	private static final Logger _log = LoggerFactory.getLogger(TvTArena3.class);
	
	private static class TvTArena3Impl extends TvTTemplate
	{
		public TvTArena3Impl()
		{
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onLoad()
		{
			_managerId = 31392;
			_className = "TvTArena3";
			_status = 0;
			_team1list = new CopyOnWriteArrayList<>();
			_team2list = new CopyOnWriteArrayList<>();
			_team1live = new CopyOnWriteArrayList<>();
			_team2live = new CopyOnWriteArrayList<>();
			_zoneListener = new ZoneListener();
			_zone = ReflectionUtils.getZone("[tvt_arena3]");
			_zone.addListener(_zoneListener);
			_team1points = new ArrayList<>();
			_team2points = new ArrayList<>();
			_team1points.add(new Location(-79383, -52724, -11518, -11418));
			_team1points.add(new Location(-79558, -52793, -11518, -11418));
			_team1points.add(new Location(-79726, -52867, -11518, -11418));
			_team1points.add(new Location(-79911, -52845, -11518, -11418));
			_team1points.add(new Location(-80098, -52822, -11518, -11418));
			_team1points.add(new Location(-80242, -52714, -11518, -11418));
			_team1points.add(new Location(-80396, -52597, -11518, -11418));
			_team1points.add(new Location(-80466, -52422, -11518, -11418));
			_team1points.add(new Location(-80544, -52250, -11518, -11418));
			_team1points.add(new Location(-80515, -52054, -11518, -11418));
			_team1points.add(new Location(-80496, -51878, -11518, -11418));
			_team1points.add(new Location(-80386, -51739, -11518, -11418));
			_team2points.add(new Location(-80270, -51582, -11518, -11418));
			_team2points.add(new Location(-80107, -51519, -11518, -11418));
			_team2points.add(new Location(-79926, -51435, -11518, -11418));
			_team2points.add(new Location(-79739, -51465, -11518, -11418));
			_team2points.add(new Location(-79554, -51482, -11518, -11418));
			_team2points.add(new Location(-79399, -51600, -11518, -11418));
			_team2points.add(new Location(-79254, -51711, -11518, -11418));
			_team2points.add(new Location(-79181, -51884, -11518, -11418));
			_team2points.add(new Location(-79114, -52057, -11518, -11418));
			_team2points.add(new Location(-79133, -52246, -11518, -11418));
			_team2points.add(new Location(-79156, -52427, -11518, -11418));
			_team2points.add(new Location(-79275, -52583, -11518, -11418));
		}
		
		@Override
		protected void onReload()
		{
			if (_status > 0)
			{
				template_stop();
			}
			_zone.removeListener(_zoneListener);
		}
	}
	
	private static TvTTemplate _instance;
	
	public static TvTTemplate getInstance()
	{
		if (_instance == null)
		{
			_instance = new TvTArena3Impl();
		}
		return _instance;
	}
	
	@Override
	public void onLoad()
	{
		CharListenerList.addGlobal(this);
		getInstance().onLoad();
		if (isActive())
		{
			spawnEventManagers();
			_log.info("Loaded Event: TvT Arena 3 [state: activated]");
		}
		else
		{
			_log.info("Loaded Event: TvT Arena 3 [state: deactivated]");
		}
	}
	
	@Override
	public void onReload()
	{
		getInstance().onReload();
		unSpawnEventManagers();
		_instance = null;
	}
	
	@Override
	public void onShutdown()
	{
		onReload();
	}
	
	@Override
	public void onDeath(Creature cha, Creature killer)
	{
		getInstance().onDeath(cha, killer);
	}
	
	@Override
	public void onPlayerExit(Player player)
	{
		getInstance().onPlayerExit(player);
	}
	
	@Override
	public void onTeleport(Player player, int x, int y, int z, Reflection reflection)
	{
		getInstance().onTeleport(player);
	}
	
	public String DialogAppend_31392(Integer val)
	{
		if (val == 0)
		{
			Player player = getSelf();
			if (player.isGM())
			{
				return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31392.htm", player) + HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31392-4.htm", player);
			}
			return HtmCache.getInstance().getNotNull("scripts/events/TvTArena/31392.htm", player);
		}
		return "";
	}
	
	public void create1()
	{
		getInstance().template_create1(getSelf());
	}
	
	public void register()
	{
		getInstance().template_register(getSelf());
	}
	
	public void check1(String[] var)
	{
		getInstance().template_check1(getSelf(), getNpc(), var);
	}
	
	public void register_check()
	{
		getInstance().template_register_check(getSelf());
	}
	
	public void stop()
	{
		getInstance().template_stop();
	}
	
	public void announce()
	{
		getInstance().template_announce();
	}
	
	public void prepare()
	{
		getInstance().template_prepare();
	}
	
	public void start()
	{
		getInstance().template_start();
	}
	
	public void timeOut()
	{
		getInstance().template_timeOut();
	}
	
	private final List<NpcInstance> _spawns = new ArrayList<>();
	
	private boolean isActive()
	{
		return IsActive("TvT Arena 3");
	}
	
	public void startEvent()
	{
		Player player = getSelf();
		if (!player.getPlayerAccess().IsEventGm)
		{
			return;
		}
		if (SetActive("TvT Arena 3", true))
		{
			spawnEventManagers();
			System.out.println("Event: TvT Arena 3 started.");
			Announcements.getInstance().announceToAll("Начался TvT Arena 3 эвент.");
		}
		else
		{
			player.sendMessage("TvT Arena 3 Event already started.");
		}
		show("admin/events.htm", player);
	}
	
	public void stopEvent()
	{
		Player player = getSelf();
		if (!player.getPlayerAccess().IsEventGm)
		{
			return;
		}
		if (SetActive("TvT Arena 3", false))
		{
			ServerVariables.unset("TvT Arena 3");
			unSpawnEventManagers();
			stop();
			System.out.println("TvT Arena 3 Event stopped.");
			Announcements.getInstance().announceToAll("TvT Arena 3 эвент окончен.");
		}
		else
		{
			player.sendMessage("TvT Arena 3 Event not started.");
		}
		show("admin/events.htm", player);
	}
	
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] =
		{
			{
				82840,
				148936,
				-3472,
				0
			}
		};
		NpcTemplate template = NpcHolder.getInstance().getTemplate(31392);
		for (int[] element : EVENT_MANAGERS)
		{
			SimpleSpawner sp = new SimpleSpawner(template);
			sp.setLocx(element[0]);
			sp.setLocy(element[1]);
			sp.setLocz(element[2]);
			sp.setHeading(element[3]);
			NpcInstance npc = sp.doSpawn(true);
			npc.setName("Arena 3");
			npc.setTitle("TvT Event");
			_spawns.add(npc);
		}
	}
	
	private void unSpawnEventManagers()
	{
		for (NpcInstance npc : _spawns)
		{
			npc.deleteMe();
		}
		_spawns.clear();
	}
}