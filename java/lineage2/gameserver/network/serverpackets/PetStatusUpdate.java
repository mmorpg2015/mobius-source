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
package lineage2.gameserver.network.serverpackets;

import lineage2.gameserver.model.Summon;
import lineage2.gameserver.utils.Location;

public class PetStatusUpdate extends L2GameServerPacket
{
	private final int type, obj_id, level;
	private final int maxFed, curFed, maxHp, curHp, maxMp, curMp;
	private final long exp, exp_this_lvl, exp_next_lvl;
	private final Location _loc;
	private final String title;
	
	public PetStatusUpdate(final Summon summon)
	{
		type = summon.getSummonType();
		obj_id = summon.getObjectId();
		_loc = summon.getLoc();
		title = summon.getTitle();
		curHp = (int) summon.getCurrentHp();
		maxHp = summon.getMaxHp();
		curMp = (int) summon.getCurrentMp();
		maxMp = summon.getMaxMp();
		curFed = summon.getCurrentFed();
		maxFed = summon.getMaxFed();
		level = summon.getLevel();
		exp = summon.getExp();
		exp_this_lvl = summon.getExpForThisLevel();
		exp_next_lvl = summon.getExpForNextLevel();
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xb6);
		writeD(type);
		writeD(obj_id);
		writeD(_loc.x);
		writeD(_loc.y);
		writeD(_loc.z);
		writeS(title);
		writeD(curFed);
		writeD(maxFed);
		writeD(curHp);
		writeD(maxHp);
		writeD(curMp);
		writeD(maxMp);
		writeD(level);
		writeQ(exp);
		writeQ(exp_this_lvl);
		writeQ(exp_next_lvl);
		writeD(0x00);
	}
}