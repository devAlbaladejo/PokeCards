import { Types } from "./types";
import { Rarities } from "./rarities";

export class Cards{
    id: number;
    name: string;
    image: string;
    primary_type: Types;
    secondary_type: Types;
    height: number;
    weight: number;
    hp: number;
    attack: number;
    defense: number;
    special_attack: number;
    special_defense: number;
    speed: number;
    rarities: Rarities;
}