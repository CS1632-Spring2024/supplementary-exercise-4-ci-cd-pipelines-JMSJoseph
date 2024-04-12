package edu.pitt.cs;

import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public interface RentACat {
	public static RentACat createInstance(InstanceType type) {
		switch (type) {
			case IMPL:
				return new RentACatImpl();
			case BUGGY:
				return new RentACatBuggy();
			case SOLUTION:
				return new RentACatSolution();
			case MOCK:
				RentACat mockRentACat = Mockito.mock(RentACat.class);
				when(mockRentACat.returnCat(anyInt())).thenReturn(true);
				when(mockRentACat.rentCat(anyInt())).thenReturn(true);
				when(mockRentACat.renameCat(anyInt(), anyString())).thenReturn(true);
				when(mockRentACat.listCats()).thenReturn("");
				doNothing().when(mockRentACat).addCat(any(Cat.class));
				return mockRentACat;

			default:
				assert (false);
				return null;
		}
	}

	// WARNING: You are not allowed to change any part of the interface.
	// That means you cannot add any method nor modify any of these methods.

	public boolean returnCat(int id);

	public boolean rentCat(int id);

	public boolean renameCat(int id, String name);

	public String listCats();

	public void addCat(Cat c);
}
