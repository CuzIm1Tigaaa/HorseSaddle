package de.cuzim1tigaaa.smartsaddle;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Getter
public class VersionMatcher {

	private static final Map<String, String> VERSION_TO_REVISION = new HashMap<>() {
		{
			this.put("1.19", "1_21_1");
			this.put("1.19.1", "1_21_1");
			this.put("1.19.2", "1_21_1");
			this.put("1.19.3", "1_21_1");
			this.put("1.19.4", "1_21_1");
			this.put("1.20", "1_21_1");
			this.put("1.20.1", "1_21_1");
			this.put("1.20.2", "1_21_1");
			this.put("1.20.3", "1_21_1");
			this.put("1.20.4", "1_21_1");
			this.put("1.20.5", "1_21_1");
			this.put("1.21", "1_21_1");
			this.put("1.21.1", "1_21_1");
			this.put("1.21.2", "1_21_1");
			this.put("1.21.3", "1_21_1");
			this.put("1.21.4", "1_21_4");
		}
	};
	/* This needs to be updated to reflect the newest available version wrapper */
	private static final String FALLBACK_REVISION = "1_21_1";

	public HorseData match() {
		String craftBukkitPackage = Bukkit.getServer().getClass().getPackage().getName();

		String rVersion;
		if(!craftBukkitPackage.contains(".v")) { // cb package not relocated (i.e. paper 1.20.5+)
			final String version = Bukkit.getBukkitVersion().split("-")[0];
			rVersion = VERSION_TO_REVISION.getOrDefault(version, FALLBACK_REVISION);
		}else {
			rVersion = craftBukkitPackage.split("\\.")[3].substring(1);
		}

		try {
			return (HorseData) Class.forName(getClass().getPackage().getName() + ".HorseData" + rVersion)
					.getDeclaredConstructor()
					.newInstance();
		}catch(ClassNotFoundException exception) {
			throw new IllegalStateException("SmartSaddle does not support server version \"" + rVersion + "\"", exception);
		}catch(ReflectiveOperationException exception) {
			throw new IllegalStateException("Failed to instantiate horse data for version " + rVersion, exception);
		}
	}
}