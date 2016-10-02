import net.dusterthefirst.lockedServers.cryption.DualLayer;

public class test {
	public static void main(String[] args) {
		System.out.println(DualLayer.encode("Um... Our Street Adress"));
		System.out.println(DualLayer.decode(DualLayer.encode("Um... Our Street Adress")));
	}
}
