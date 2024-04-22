import { Cards } from "./cards";
import { Users } from "./users";

export class ExchangeOffers{
    id: number;
    userOffer: Users;
    cardOffer: Cards;
    desiredCard1: Cards;
    desiredCard2: Cards;
    desiredCard3: Cards;
    active: number;
}