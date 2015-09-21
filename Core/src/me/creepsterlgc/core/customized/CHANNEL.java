package me.creepsterlgc.core.customized;

public class CHANNEL {

	private String id;
	private String trigger;
	private String name;
	private String prefix;
	private String suffix;
	private String format;
	private String range;
	
	public CHANNEL(String id, String trigger, String name, String prefix, String suffix, String format, String range) {
		this.id = id;
		this.trigger = trigger;
		this.name = name;
		this.prefix = prefix;
		this.suffix = suffix;
		this.format = format;
		this.range = range;
	}
	
	public void setID(String id) { this.id = id; }
	public void setTrigger(String trigger) { this.trigger = trigger; }
	public void setName(String name) { this.name = name; }
	public void setPrefix(String prefix) { this.prefix = prefix; }
	public void setSuffix(String suffix) { this.suffix = suffix; }
	public void setFormat(String format) { this.format = format; }
	public void setRange(String range) { this.range = range; }
	
	public String getID() { return id; }
	public String getTrigger() { return trigger; }
	public String getName() { return name; }
	public String getPrefix() { return prefix; }
	public String getSuffix() { return suffix; }
	public String getFormat() { return format; }
	public String getRange() { return range; }
	
}
