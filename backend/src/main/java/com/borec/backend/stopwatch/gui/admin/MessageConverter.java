package com.borec.backend.stopwatch.gui.admin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.borec.backend.entity.Zprava;

public class MessageConverter {

    /**
     * Converts JPA entity Message -> JavaFX wrapper MessageFx
     */
    public static FXScreen.MessageFx toFx(Zprava entity) {
        if (entity == null) return null;

        LocalDate validFrom = entity.getCas_od() != null
                ? entity.getCas_od().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        LocalDate validTo = entity.getCas_do() != null
                ? entity.getCas_do().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        return new FXScreen.MessageFx(
                entity.getId(),
                validFrom,
                validTo,
                entity.getZapnuto() != null && entity.getZapnuto(),
                entity.getTitulek(),
                entity.getZprava()
        );
    }

    /**
     * Converts JavaFX wrapper MessageFx -> JPA entity Message
     */
    public static Zprava toEntity(FXScreen.MessageFx fx) {
        if (fx == null) return null;

        Date validFrom = fx.getValidFrom() != null
                ? Date.from(fx.getValidFrom().atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;

        Date validTo = fx.getValidTo() != null
                ? Date.from(fx.getValidTo().atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;

        Zprava entity = new Zprava();
        entity.setId(fx.getId() != 0 ? fx.getId() : null);
        entity.setCas_od(validFrom);
        entity.setCas_do(validTo);
        entity.setZapnuto(fx.isActive());
        entity.setTitulek(fx.getTitle());
        entity.setZprava(fx.getMessageText());

        return entity;
    }
}
