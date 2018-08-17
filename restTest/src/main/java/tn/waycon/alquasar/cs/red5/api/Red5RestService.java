package tn.waycon.alquasar.cs.red5.api;

public interface Red5RestService extends AppRestService,StreamRestService
{
	/**
	 * Should return info about system OS infos: os name, arch, system memory, File
	 * system info, cpu info jvm info: version, arch, jvm memory
	 * 
	 * @return info
	 */
	Object getSystemInfo();

}
