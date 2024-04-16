import { Types } from "./types";
import { Rarities } from "./rarities";

export class Cards{
    id: number;
    name: string;
    image: string;
    primaryType: Types;
    secondaryType: Types;
    height: number;
    weight: number;
    hp: number;
    attack: number;
    defense: number;
    specialAttack: number;
    specialDefense: number;
    speed: number;
    rarities: Rarities;
    generation: string;
    description: string;
}