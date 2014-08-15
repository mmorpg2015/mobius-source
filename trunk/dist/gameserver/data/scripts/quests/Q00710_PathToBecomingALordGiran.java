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
package quests;

import lineage2.gameserver.data.xml.holder.ResidenceHolder;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.entity.residence.Castle;
import lineage2.gameserver.model.instances.NpcInstance;
import lineage2.gameserver.model.quest.Quest;
import lineage2.gameserver.model.quest.QuestState;
import lineage2.gameserver.network.serverpackets.components.NpcString;
import lineage2.gameserver.scripts.Functions;
import lineage2.gameserver.scripts.ScriptFile;

/**
 * @author Smo
 */
public class Q00710_PathToBecomingALordGiran extends Quest implements ScriptFile
{
	private static final int Saul = 35184;
	private static final int Gesto = 30511;
	private static final int Felton = 30879;
	private static final int CargoBox = 32243;
	
	private static final int FreightChest = 13014;
	private static final int GestoBox = 13013;
	
	private static final int[] Mobs =
	{
		20832,
		20833,
		20835,
		21602,
		21603,
		21604,
		21605,
		21606,
		21607,
		21608,
		21609
	};
	
	private static final int GiranCastle = 3;
	
	public Q00710_PathToBecomingALordGiran()
	{
		super(false);
		addStartNpc(Saul);
		addTalkId(Gesto, Felton, CargoBox);
		addQuestItem(FreightChest, GestoBox);
		addKillId(Mobs);
	}
	
	@Override
	public String onEvent(String event, QuestState st, NpcInstance npc)
	{
		String htmltext = event;
		Castle castle = ResidenceHolder.getInstance().getResidence(GiranCastle);
		if (castle.getOwner() == null)
		{
			return "Castle has no lord";
		}
		Player castleOwner = castle.getOwner().getLeader().getPlayer();
		if (event.equals("saul_q710_03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		else if (event.equals("gesto_q710_03.htm"))
		{
			st.setCond(3);
		}
		else if (event.equals("felton_q710_02.htm"))
		{
			st.setCond(4);
		}
		else if (event.equals("saul_q710_07.htm"))
		{
			Functions.npcSay(npc, NpcString.S1_HAS_BECOME_THE_LORD_OF_THE_TOWN_OF_GIRAN, st.getPlayer().getName());
			castle.getDominion().changeOwner(castleOwner.getClan());
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		Castle castle = ResidenceHolder.getInstance().getResidence(GiranCastle);
		if (castle.getOwner() == null)
		{
			return "Castle has no lord";
		}
		Player castleOwner = castle.getOwner().getLeader().getPlayer();
		if (npcId == Saul)
		{
			if (cond == 0)
			{
				if (castleOwner == st.getPlayer())
				{
					if (castle.getDominion().getLordObjectId() != st.getPlayer().getObjectId())
					{
						htmltext = "saul_q710_01.htm";
					}
					else
					{
						htmltext = "saul_q710_00.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				{
					htmltext = "saul_q710_00a.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if (cond == 1)
			{
				st.setCond(2);
				htmltext = "saul_q710_04.htm";
			}
			else if (cond == 2)
			{
				htmltext = "saul_q710_05.htm";
			}
			else if (cond == 9)
			{
				htmltext = "saul_q710_06.htm";
			}
		}
		else if (npcId == Gesto)
		{
			if (cond == 2)
			{
				htmltext = "gesto_q710_01.htm";
			}
			else if ((cond == 3) || (cond == 4))
			{
				htmltext = "gesto_q710_04.htm";
			}
			else if (cond == 5)
			{
				st.takeAllItems(FreightChest);
				st.setCond(7);
				htmltext = "gesto_q710_05.htm";
			}
			else if (cond == 7)
			{
				htmltext = "gesto_q710_06.htm";
			}
			else if (cond == 8)
			{
				st.takeAllItems(GestoBox);
				st.setCond(9);
				htmltext = "gesto_q710_07.htm";
			}
			else if (cond == 9)
			{
				htmltext = "gesto_q710_07.htm";
			}
			
		}
		else if (npcId == Felton)
		{
			if (cond == 3)
			{
				htmltext = "felton_q710_01.htm";
			}
			else if (cond == 4)
			{
				htmltext = "felton_q710_03.htm";
			}
		}
		else if (npcId == CargoBox)
		{
			if (cond == 4)
			{
				st.setCond(5);
				st.giveItems(FreightChest, 1);
				htmltext = "box_q710_01.htm";
			}
			else if (cond == 5)
			{
				htmltext = "box_q710_02.htm";
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(NpcInstance npc, QuestState st)
	{
		if (st.getCond() == 7)
		{
			if (st.getQuestItemsCount(GestoBox) < 300)
			{
				st.giveItems(GestoBox, 1);
			}
			if (st.getQuestItemsCount(GestoBox) >= 300)
			{
				st.setCond(8);
			}
		}
		return null;
	}
	
	@Override
	public void onLoad()
	{
	}
	
	@Override
	public void onReload()
	{
	}
	
	@Override
	public void onShutdown()
	{
	}
}