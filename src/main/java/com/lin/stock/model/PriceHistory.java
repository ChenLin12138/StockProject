package com.lin.stock.model;

import java.util.Date;

public class PriceHistory {
    private Long pk;

    private String code;
    
    private String date;

    private Float tclose;

    private Float high;

    private Float low;

    private Float topen;

    private Float chg;

    private Float pchg;

    private Float turnoverrate;

    private Integer voturnover;

    private Float vaturnover;

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getTclose() {
        return tclose;
    }

    public void setTclose(Float tclose) {
        this.tclose = tclose;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getTopen() {
        return topen;
    }

    public void setTopen(Float topen) {
        this.topen = topen;
    }

    public Float getChg() {
        return chg;
    }

    public void setChg(Float chg) {
        this.chg = chg;
    }

    public Float getPchg() {
        return pchg;
    }

    public void setPchg(Float pchg) {
        this.pchg = pchg;
    }

    public Float getTurnoverrate() {
        return turnoverrate;
    }

    public void setTurnoverrate(Float turnoverrate) {
        this.turnoverrate = turnoverrate;
    }

    public Integer getVoturnover() {
        return voturnover;
    }

    public void setVoturnover(Integer voturnover) {
        this.voturnover = voturnover;
    }

    public Float getVaturnover() {
        return vaturnover;
    }

    public void setVaturnover(Float vaturnover) {
        this.vaturnover = vaturnover;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceHistory other = (PriceHistory) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
    
    
    
}