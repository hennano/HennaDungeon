package net.hennabatch.hennadungeon.mission.help;

import net.hennabatch.hennadungeon.entity.character.HelpedPartyMemberEntity;
import net.hennabatch.hennadungeon.mission.Phase;

public class GiveWeaponPhase extends Phase{
    @Override
    public boolean shouldExecute() {
        return getDungeon().getEntities().stream().noneMatch(x -> x instanceof HelpedPartyMemberEntity);
    }

    @Override
    public void execute() {
    }
}
