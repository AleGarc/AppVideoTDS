package persistencia;

import java.util.List;
import modelo.VideoList;

public interface IAdaptadorVideoListDAO {
	
	public void registrarVideoList(VideoList videoList);
	public void borrarVideoList(VideoList videoList);
	public void modificarVideoList(VideoList videoList);
	public VideoList recuperarVideoList(int codigo);
	public List<VideoList> recuperarTodosVideoLists();
						

}
