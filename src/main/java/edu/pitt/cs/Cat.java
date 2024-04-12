package edu.pitt.cs;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public interface Cat {
	public static Cat createInstance(InstanceType type, int id, String name) {
		switch (type) {
			case IMPL:
				return new CatImpl(id, name);
			case BUGGY:
				return new CatBuggy(id, name);
			case SOLUTION:
				return new CatSolution(id, name);
			case MOCK:
				Cat mockCat = Mockito.mock(Cat.class);
				when(mockCat.getId()).thenReturn(id);
				when(mockCat.getName()).thenReturn(name);
				when(mockCat.toString()).thenReturn("ID " + id + ". " + name);

				when(mockCat.getRented()).thenReturn(false);

				doAnswer(invocation -> {
					when(mockCat.getRented()).thenReturn(true);
					return null;
				}).when(mockCat).rentCat();

				doAnswer(invocation -> {
					when(mockCat.getRented()).thenReturn(false);
					return null;
				}).when(mockCat).returnCat();

				doAnswer(invocation -> {
					String newName = invocation.getArgument(0);
					when(mockCat.getName()).thenReturn(newName);
					when(mockCat.toString()).thenReturn("ID " + id + ". " + newName);
					return null;
				}).when(mockCat).renameCat(anyString());

				return mockCat;

			default:
				assert (false);
				return null;
		}
	}

	// WARNING: You are not allowed to change any part of the interface.
	// That means you cannot add any method nor modify any of these methods.

	public void rentCat();

	public void returnCat();

	public void renameCat(String name);

	public String getName();

	public int getId();

	public boolean getRented();

	public String toString();
}
