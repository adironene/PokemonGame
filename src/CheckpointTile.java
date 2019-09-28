
public class CheckpointTile extends Tile {
	//if player lands here, respawn point is set to here
	public CheckpointTile(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void triggerEvent() {
//		System.out.println("checkpoint set!");
		getPlayer().setCheckPointxCo(xCo);
		getPlayer().setCheckPointyCo(yCo);
		getPlayer().healAll();
//		System.out.println("should be triggering"+" x: "+getPlayer().getCheckPointRectX()+ " y: "+getPlayer().getCheckPointRectY());
	}

	@Override
	protected void setIntroInFinished(boolean b) {
		// TODO Auto-generated method stub
		
	}
}



