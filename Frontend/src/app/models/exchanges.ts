import { Cards } from "./cards";
import { ExchangeOffers } from "./exchange-offers";
import { Users } from "./users";

export class Exchanges{
    id: number;
    exchangeOffer: ExchangeOffers;
    userDemand: Users;
    cardDemand: Cards;
}