import { describe, expect, it } from "vitest";
import { TestDriver } from "testdriverai/vitest/hooks";

describe("BlackJack Game", () => {
  it("should load the game with initial hands and controls", async (context) => {
    const testdriver = TestDriver(context);

    await testdriver.provision.chrome({ url: "https://games-site.github.io/projects/blackjack/index.html" });

    const assertResult = await testdriver.assert("Dealer Cards and Your Cards are displayed with Hit and Stand buttons");
    expect(assertResult).toBeTruthy();

    await testdriver.find("Hit button").click();

    const hitResult = await testdriver.assert("The player received an additional card");
    expect(hitResult).toBeTruthy();
  });

  it("should allow the player to stand and reveal dealer cards", async (context) => {
    const testdriver = TestDriver(context);

    await testdriver.provision.chrome({ url: "https://games-site.github.io/projects/blackjack/index.html" });

    await testdriver.find("Stand button").click();

    const assertResult = await testdriver.assert("The dealer hand is revealed and the Retry button is displayed");
    expect(assertResult).toBeTruthy();
  });
});
